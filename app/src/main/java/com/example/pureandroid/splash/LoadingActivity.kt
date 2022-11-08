package com.example.pureandroid.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.pureandroid.MainActivity
import com.example.pureandroid.R

/**
 * 学习价值：
 * 1，kotlin 反编译：默认反编译工具： jadx,有疑问再 d2j-dex2jar。 看 回调 lamda 等在 字节码里到底长什么样，就一清二楚了，而不是被 kotlin上层给封装蒙蔽眼睛
 */

// Android - setVisibility() 失效，竟然是因为内存泄露
// 作者：GitLqr
//链接：https://juejin.cn/post/7115781013170552840
//来源：稀土掘金
//著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
/**
 * App 启动时的 Loading 界面
 *
 * @author GitLqr
 * @since 2022/7/2
 */
class LoadingActivity : FragmentActivity() {
    // AppCompatActivity crash Caused by: java.lang.IllegalStateException: You need to use a Theme.AppCompat theme (or descendant) with this activity.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        // Loading 模块加载器
        LoadingLoader.init(onLoadFail, onLoadComplete).go()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("GitLqr", "onDestroy")
    }

    private val onLoadFail: () -> Unit = {
        // 显示默认 loading 界面
        findViewById<View>(R.id.cl_def_loading).setVisibility(View.VISIBLE)
    }

    private val onLoadComplete: () -> Unit = {
        // 模拟初始化数据，1秒后跳转主界面
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            // 注意：此处意图使用的 flag，会将 LoadingActivity 界面关闭，触发 onDestroy()
            startActivity(intent)
        }, 1000)
    }
}