package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Google Gemini APIを呼び出す共通クラス
 * 
 * ============================================
 * 【重要】このクラスは講師が用意する共通部品です
 * ============================================
 * 
 * 学生は このクラスのask()メソッドを呼ぶだけでAIの回答を取得できます。
 * 
 * ■ 使い方（たった2行）
 * String question = "JavaのArrayListとは何ですか？";
 * String aiAnswer = GeminiAPI.ask(question);
 * 
 * ■ 仕組み
 * 1. ユーザーの質問をJSON形式に組み立てる
 * 2. Google Gemini APIにHTTPリクエストを送信
 * 3. レスポンス（JSON）からAIの回答テキストを取り出す
 * 4. 回答テキストをStringで返す
 * 
 * ■ APIキーの取得方法
 * 1. https://aistudio.google.com/ にアクセス
 * 2. Googleアカウントでログイン
 * 3. 「Get API key」→「Create API key」をクリック
 * 4. 生成されたキーをコピーして下のAPI_KEYに貼り付ける
 * ※ クレジットカード不要・無料で使えます
 */
public class GeminiAPI {

	// ============================================
	// ★ ここに自分のAPIキーを貼り付けてください
	// ============================================
	private static final String API_KEY = "AIzaSyCrgITDRX5FuuoyxsK4MAoFtoOX0t-VwOQ";

	// 使用するモデル（無料枠で利用可能）
	private static final String MODEL = "gemini-2.5-flash";

	// APIのURL
	private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/"
			+ MODEL + ":generateContent?key=" + API_KEY;

	/**
	 * AIに質問して回答を取得する
	 * 
	 * @param question ユーザーの質問文
	 * @return AIの回答テキスト。エラー時はエラーメッセージを返す。
	 * 
	 * 【使         方の例】
	 * St         ing answer = GeminiAPI.ask("Javaのfor文について教えてください");
	 * Sy         tem.out.println(answer);
	 */
	public static String ask(String question) {
		try {
			// ============================================
			// Step 1: HTTPリクエストの準備
			// ============================================
			URL url = new URL(API_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setConnectTimeout(30000); // 接続タイムアウト30秒
			conn.setReadTimeout(60000); // 読み取りタ ト60秒

			// ============================================
			// Step 2: リクエストボディ（JSON）を作成
			// ============================================
			// Gemini APIに送るJSONの形式:
			// {
			//   "conte ": [{
			//     "par [{ "text": "質問内容" }]
			//   }] 
			// }
			//
			// 【注意】特殊文字のエスケープ処理
			// ユーザーの入力に " や \ や改行が含まれるとJSONが壊れるため、
			// エスケープ処理を行っています。
			String escapedQuestion = question
					.replace("\\", "\\\\") // \ → \\ 
					.replace("\"", "\\\"") // " → \" 
					.replace("\n", "\\n") // 改行 → \n 
					.replace("\r", "\\r") // 復帰 → \r 
					.replace("\t", "\\t"); // タブ → \t 

			String jsonBody = "{\"contents\":[{\"parts\":[{\"text\":\""
					+ escapedQuestion + "\"}]}]}";

			// ============================================
			// Step 3: リクエストを送信
			// ============================================
			try (OutputStream os = conn.getOutputStream()) {
				os.write(jsonBody.getBytes("UTF-8"));
			}

			// ============================================
			// Step 4: レスポンスを受信
			// ============================================
			int responseCode = conn.getResponseCode();

			if (responseCode == 200) {
				// 正常なレスポンスを読み取る
				StringBuilder response = new StringBuilder();
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
					String line;
					while ((line = br.readLine()) != null) {
						response.append(line);
					}
				}

				// ============================================
				// Step 5: JSONからAIの回答テキストを取り出す
				// ============================================
				return extractTextFromResponse(response.toString());

			} else {
				// エラーレスポンスを読み取る
				StringBuilder errorResponse = new StringBuilder();
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(conn.getErrorStream(), "UTF-8"))) {
					String line;
					while ((line = br.readLine()) != null) {
						errorResponse.append(line);
					}
				}
				return "【AIエラー】APIリクエストが失敗しました（コード: " + responseCode + "）";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "【AIエラー】AI APIとの通信に失敗しました: " + e.getMessage();
		}
	}

	/**
	 * Gemini APIのレスポンスJSONからテキスト部分を取り出す
	 * 
	 * 【解説】
	 * レスポンスJSONの中から "text" : "回答内容" の部分を探して取り出します。
	 * 
	 * 本来はJSONライブラリ（Gson等）を使うべきですが、
	 * 外部ライブラリの導入を最小限にするため、文字列操作で取り出しています。
	 * 
	 * レスポンスの構造:
	 * {
	 *    ndidates": [{
	 *    ntent": {
	 *    rts": [{ "text": "AIの回答がここに入る" }],
	 *    le": "model"
	 *    
	 *    
	 * }
	 */
	private static String extractTextFromResponse(String json) {
		try {
			// "text" : " の位置を探す
			String searchKey = "\"text\"";
			int textIndex = json.indexOf(searchKey);

			if (textIndex == -1) {
				return "【AIエラー】回答の取得に失敗しました";
			}

			// "text" の後の : を見つけて、その後の " を探す
			int colonIndex = json.indexOf(":", textIndex + searchKey.length());
			int startQuote = json.indexOf("\"", colonIndex + 1);
			if (startQuote == -1) {
				return "【AIエラー】回答の解析に失敗しました";
			}

			// 開始 " の後から、対応する閉じ " を探す（エスケープ考慮）
			StringBuilder result = new StringBuilder();
			int i = startQuote + 1;
			while (i < json.length()) {
				char c = json.charAt(i);
				if (c == '\\' && i + 1 < json.length()) {
					// エスケープシーケンスの処理
					char next = json.charAt(i + 1);
					switch (next) {
					case 'n':
                            
                            
						result.append('\n');
                            
                            
						break;
                            
                            
					case 'r':
                            
                            
						result.append('\r');
                            
                            
						break;
                            
                            
					case 't':
						result.append('\t');
						break;
					case '"':
						result.append('"');
						break;
					case '\\':
						result.append('\\');
						break;
					default:
						result.append(next);
						break;
					}
					i += 2;
				} else if (c == '"') {
					// 閉じ " に到達
					break;
				} else {
					result.append(c);
					i++;
				}
			}

			return result.toString().trim();

		} catch (Exception e) {
			return "【AIエラー】回答の解析中にエラーが発生しました";
		}
	}
}
