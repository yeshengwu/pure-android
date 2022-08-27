package com.example.pureandroid.splash

import android.os.Handler
import android.os.Looper
import android.util.Log

// 作者：GitLqr
//链接：https://juejin.cn/post/7115781013170552840
//来源：稀土掘金
//著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
/**
 * Loading 加载器
 *
 * @author GitLqr
 * @since 2022/7/2
 */
object LoadingLoader {

    private var isInited = false // 防止多次初始化
    private lateinit var onLoadFail: () -> Unit // 远程loading加载失败时的回调
    private lateinit var onLoadComplete: () -> Unit // 加载完成后回调（无论成功失败）

    fun init(onLoadFail: () -> Unit = {}, onLoadComplete: () -> Unit = {}): LoadingLoader {
        if (!isInited) {
            this.onLoadFail = onLoadFail
            this.onLoadComplete = onLoadComplete
            isInited = true
        } else {
            log("you have inited, this time is not valid")
        }
        return this
    }

    fun go() {
        if (isInited) {
            loadRemoteLoading(callback = { isSuccess ->
                if (!isSuccess) onLoadFail()
                onLoadComplete()

                destroy() // 使命完成，释放资源
            })
        } else {
            log("you must invoke init() firstly")
        }
    }

    private fun loadRemoteLoading(callback: (boolean: Boolean) -> Unit) {
        // 模拟远程 Loading 模块加载失败
        Handler(Looper.getMainLooper()).postDelayed({
            callback(false)
        }, 1000)
    }

    private fun log(msg: String) {
        Log.e("LoadingUpdater", msg)
    }

    fun destroy() {
        this.onLoadFail = {}
        this.onLoadComplete = {}
        this.isInited = false
    }
}

