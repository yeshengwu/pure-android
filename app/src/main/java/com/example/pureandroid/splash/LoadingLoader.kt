package com.example.pureandroid.splash

import android.os.Handler
import android.os.Looper
import android.util.Log

// 默认反编译工具： jadx

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

    // 反编译为 函数对象
    // private static Functions<Unit> onLoadComplete;
    // private static Functions<Unit> onLoadFail;

    // 匿名函数 定义：https://developer.android.com/kotlin/learn#class-functions
    //并非每个函数都需要一个名称。某些函数通过输入和输出更直接地进行标识。这些函数称为“匿名函数”。
    // 您可以保留对某个匿名函数的引用，以便日后使用此引用来调用该匿名函数。与其他引用类型一样，您也可以在应用中传递引用。
    // 匿名函数
    private val stringLengthFunc: (String) -> Int = { input ->
        input.length
    }

    // stringLengthFunc 包含对一个匿名函数的引用
    // 该函数将 String 当作输入，并将输入 String 的长度作为 Int 类型的输出返回。
    // 因此，该函数的类型表示为 (String) -> Int。
    // 不过，此代码不会调用该函数。要检索该函数的结果，您必须像调用命名函数一样调用该函数。调用 stringLengthFunc 时，必须提供 String，如以下示例所示：
    // val stringLength: Int = stringLengthFunc("Android")

    // 匿名函数
    private val onLoadFailEvan: (name: String, age: Int) -> String =
        { nameInput: String, ageInput: Int ->
            nameInput + ageInput
        }

    //反编译为
    //   private static final Function1<String, Integer> stringLengthFunc = LoadingLoader$stringLengthFunc$1.INSTANCE;
    //    private static final Function2<String, Integer, String> onLoadFailEvan = LoadingLoader$onLoadFailEvan$1.INSTANCE;

    // LoadingLoader$onLoadFailEvan$1 为单独一个类：Function2： 2个参数的函数对象。
    // final class LoadingLoader$onLoadFailEvan$1 extends Lambda implements Function2<String, Integer, String> {
    //
    //     public /* bridge */ /* synthetic */ String invoke(String str, Integer num) {
    //          //
    //     }
    // }

    fun init2(
        onLoadFail: (name: String, age: Int) -> String = { s: String, i: Int -> s + i },
        onLoadComplete: () -> Unit = {}
    ): LoadingLoader {
        log("init2 test")
        return this
    }
    // init2 反编译为：
    //   public final LoadingLoader init2(Function2<? super String, ? super Integer, String> onLoadFail2, Functions<Unit> onLoadComplete2) {
    //        Intrinsics.checkParameterIsNotNull(onLoadFail2, "onLoadFail");
    //        Intrinsics.checkParameterIsNotNull(onLoadComplete2, "onLoadComplete");
    //        log("init2 test");
    //        return this;
    //    }

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

    // 反编译： 但这里不知为啥是这样 ？？？ Function1.this.invoke(false);
    // private final void loadRemoteLoading(final Function1<? super Boolean, Unit> function1) {
    //        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.example.pureandroid.splash.LoadingLoader$loadRemoteLoading$1
    //            @Override // java.lang.Runnable
    //            public final void run() {
    //                Function1.this.invoke(false);
    //            }
    //        }, 1000L);
    //    }

    // 把这个类放入其他工程编译，再用 d2j-dex2jar 来反编译： this.$callback.invoke(Boolean.valueOf(false)); 看上去正常
    // private final void loadRemoteLoading(Function1<? super Boolean, Unit> paramFunction1) {
    //    (new Handler(Looper.getMainLooper())).postDelayed(new LoadingLoader$loadRemoteLoading$1(paramFunction1), 1000L);
    //  }
    //
    // static final class LoadingLoader$loadRemoteLoading$1 implements Runnable {
    //    LoadingLoader$loadRemoteLoading$1(Function1 param1Function1) {}
    //
    //    public final void run() {
    //      this.$callback.invoke(Boolean.valueOf(false));
    //    }
    //  }

    private fun log(msg: String) {
        Log.e("LoadingUpdater", msg)
    }

    fun destroy() {
        this.onLoadFail = {}
        this.onLoadComplete = {}
        this.isInited = false
    }
}

