package sn.mini.kotlin.util.http

import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


object HttpUtil {
    val okHttpClient: OkHttpClient? = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) //连接超时时间
            .writeTimeout(10, TimeUnit.SECONDS) // 写数据超时时间
            .readTimeout(30, TimeUnit.SECONDS) // 读数据超时时间
            .build()

    /**
     * 发送get请求
     * @param client
     * @param url
     */
    fun get(client: OkHttpClient?, url: String): Response? = get(client, Request.Builder().url(url).get().build())

    /**
     * 发送get请求
     * @param client
     * @param request
     */
    fun get(client: OkHttpClient?, request: Request) = client?.newCall(request)?.execute()

    /**
     * 发送post请求
     * @param client
     * @param url
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(client: OkHttpClient?, url: String) = post(client, url, FormBody.Builder().build())

    /**
     * 发送post请求
     * @param client
     * @param url
     * @param requestBody
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(client: OkHttpClient?, url: String, requestBody: RequestBody) = post(client, Request.Builder().url(url).post(requestBody).build())

    /**
     * 发送post请求
     * @param client
     * @param request
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(client: OkHttpClient?, request: Request) = client?.newCall(request)?.execute()


    /**
     * 发送get请求
     * @param client
     * @param url
     * @param onFailure
     * @param onResponse
     */
    fun get(client: OkHttpClient?, url: String, onFailure: (Call, IOException) -> Unit?, onResponse: (Call, Response) -> Unit?) = //
            get(client, Request.Builder().url(url).get().build(), onFailure, onResponse)

    /**
     * 发送get请求
     * @param client
     * @param request
     * @param onFailure
     * @param onResponse
     */
    fun get(client: OkHttpClient?, request: Request, onFailure: (Call, IOException) -> Unit?, onResponse: (Call, Response) -> Unit?) = //
            client?.newCall(request)?.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure(call, e)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    onResponse(call, response)
                }
            })

    /**
     * 发送post请求
     * @param client
     * @param url
     * @param onFailure
     * @param onResponse
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(client: OkHttpClient?, url: String, onFailure: (Call, IOException) -> Unit?, onResponse: (Call, Response) -> Unit?) = //
            post(client, url, FormBody.Builder().build(), onFailure, onResponse)

    /**
     * 发送post请求
     * @param client
     * @param url
     * @param requestBody
     * @param onFailure
     * @param onResponse
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(client: OkHttpClient?, url: String, requestBody: RequestBody, onFailure: (Call, IOException) -> Unit?, //
             onResponse: (Call, Response) -> Unit?) = post(client, Request.Builder().url(url).post(requestBody).build(), onFailure, onResponse)

    /**
     * 发送post请求
     * @param client
     * @param request
     * @param onFailure
     * @param onResponse
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(client: OkHttpClient?, request: Request, onFailure: (Call, IOException) -> Unit?, onResponse: (Call, Response) -> Unit?) = //
            client?.newCall(request)?.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure(call, e)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    onResponse(call, response)
                }
            })

    ///////////////////////////////
    /**
     * 发送get请求
     * @param url
     */
    fun get(url: String): Response? = get(okHttpClient, url)

    /**
     * 发送get请求
     * @param request
     */
    fun get(request: Request) = get(okHttpClient, request)

    /**
     * 发送post请求
     * @param url
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(url: String) = post(okHttpClient, url)

    /**
     * 发送post请求
     * @param url
     * @param requestBody
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(url: String, requestBody: RequestBody) = post(okHttpClient, url, requestBody)

    /**
     * 发送post请求
     * @param request
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(request: Request) = post(okHttpClient, request)


    /**
     * 发送get请求
     * @param url
     * @param onFailure
     * @param onResponse
     */
    fun get(url: String, onFailure: (Call, IOException) -> Unit?, onResponse: (Call, Response) -> Unit?) = //
            get(okHttpClient, url, onFailure, onResponse)

    /**
     * 发送get请求
     * @param request
     * @param onFailure
     * @param onResponse
     */
    fun get(request: Request, onFailure: (Call, IOException) -> Unit?, onResponse: (Call, Response) -> Unit?) = //
            get(okHttpClient, request, onFailure, onResponse)

    /**
     * 发送post请求
     * @param url
     * @param onFailure
     * @param onResponse
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(url: String, onFailure: (Call, IOException) -> Unit?, onResponse: (Call, Response) -> Unit?) = //
            post(okHttpClient, url, onFailure, onResponse)

    /**
     * 发送post请求
     * @param url
     * @param requestBody
     * @param onFailure
     * @param onResponse
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(url: String, requestBody: RequestBody, onFailure: (Call, IOException) -> Unit?, onResponse: (Call, Response) -> Unit?) = //
            post(okHttpClient, url, requestBody, onFailure, onResponse)

    /**
     * 发送post请求
     * @param request
     * @param onFailure
     * @param onResponse
     * @see okhttp3.FormBody.Builder
     * @see okhttp3.MultipartBody.Builder
     * @see okhttp3.RequestBody.create
     * @see okhttp3.MediaType.parse
     */
    fun post(request: Request, onFailure: (Call, IOException) -> Unit?, onResponse: (Call, Response) -> Unit?) = //
            post(okHttpClient, request, onFailure, onResponse)
}
