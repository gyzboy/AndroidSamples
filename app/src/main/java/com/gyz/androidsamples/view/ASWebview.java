package com.gyz.androidsamples.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebIconDatabase;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyz.androidsamples.BuildConfig;
import com.gyz.androidsamples.R;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

/**
 * Created by guoyizhe on 16/6/28.
 * 邮箱:gyzboy@126.com
 */
public class ASWebview extends Activity {
    private WebView wb;
    private Button save;
    private Button up;
    private Button down;
    private ImageView iv_icon;
    private final String TAG = ASWebview.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        wb = (WebView) findViewById(R.id.webview);
        save = (Button) findViewById(R.id.btn_save);
        up = (Button) findViewById(R.id.btn_pageup);
        down = (Button) findViewById(R.id.btn_pagedown);
        iv_icon = (ImageView) findViewById(R.id.icon);
//        wb.getSettings().setJavaScriptEnabled(true);
//        wb.loadUrl("http://www.baidu.com");//有重定向
        wb.loadUrl("http://www.jb51.com");//没有重定向


//        String summary = "<html><body>测试含有中文的 <b>HTML</b></body></html>";
//        wb.getSettings().setDefaultTextEncodingName("UTF-8");
        //loadData()中的html data中不能包含'#', '%', '\', '?'四中特殊字符，这就为我们内嵌css等制造了些许麻烦，因为css中经常用'#', '%'等字符，
        //我们需要用UrlEncoder编码为%23, %25, %27, %3f，通常使用loadDataWithBaseUrl
//        wb.loadData(summary, "text/html;charset=UTF-8", null);//加载html代码片

//        wb.loadDataWithBaseURL(null,summary,"text/html","utf-8",null);
//        wb.postUrl(String url, byte[] postData) webview使用"Post"方法加载URL ,附带postData 数据。


        //在onCreate()方法里设置icon存储的路径
        WebIconDatabase.getInstance().open(getDir("icons", MODE_PRIVATE).getPath());

        setWebMethods();
        setWebSettings();
        setWebClient();
        setWebChromeClient();

        //wb.loadUrl("javascript:handler.getDate('" + date.replace("年", "-").replace("月", "-").replace("日", "") + "')"); 调用服务器js方法
        wb.addJavascriptInterface(new CustomHandler(), "handler");//js中定义的对象名称

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File sdCard = Environment.getExternalStorageDirectory();
                    File dir = new File(sdCard.getAbsolutePath() + "/ASWebview/");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    wb.saveWebArchive(dir.toString() + File.separator + "SavedArchive.xml");
                    //mWebView.saveWebArchive(filename);
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wb.pageUp(true);//向上滚动WebView页面大小一半内容
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wb.pageDown(true);//向下滚动WebView页面大小一半内容
            }
        });

    }

    /**
     * 服务器调用本地方法,需要在主线程中进行,否则容易出现crash
     */
    class CustomHandler {
        @JavascriptInterface
        public void showDateDialog() {
            handler.obtainMessage(1).sendToTarget();
        }

        @JavascriptInterface
        public void back() {
            handler.obtainMessage(3).sendToTarget();
        }
    }

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
                case 3:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 11) {
            wb.onResume();
        }
        wb.resumeTimers();
        wb.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wb.pauseTimers();
        if (Build.VERSION.SDK_INT >= 11) {
            wb.onPause();//onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。并且可以减少不必要的CPU和网络开销，可以达到省电、省流量、省资源的效果
        }
        wb.getSettings().setJavaScriptEnabled(false);
    }

    private void setWebMethods() {
//        wb.setHttpAuthUsernamePassword();//与WebViewClient#onReceivedHttpAuthRequest一起使用
//        wb.destroy();//在webview从window上detach后调用
//wb.setNetworkAvailable(true);
//        wb.capturePicture();
//        wb.getContentHeight();
//        wb.pauseTimers();//暂停webview 所有布局,解析,javaScript times计时器,针对全局的webview起效
//        wb.clearXXX();//清除数据


        WebBackForwardList list = wb.copyBackForwardList();
        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.getItemAtIndex(i).getTitle());
        }

        wb.setOnCreateContextMenuListener(new CustomContextMenuListener());
        wb.setDownloadListener(new CustomDownloadLsn());

        //消除白色底图
        wb.setScrollbarFadingEnabled(true);
        wb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        wb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//Android开启硬件加速的话view切换时可能引起闪屏,这是系统级的BUG,关闭之可缓解,但滑动起来感觉卡顿

    }

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private void setWebChromeClient() {
        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //当网页加载进度变化时调用
                Log.d("pageProgress", String.valueOf(newProgress));
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                iv_icon.setImageBitmap(icon);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("title", title);
            }

            CustomViewCallback customCallback;

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                //当前web页面全屏时调用
                customCallback = callback;
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                //退出全屏时调用
                customCallback.onCustomViewHidden();
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
                //<a> 标签的 target 属性:
                //<a href="http://www.baidu.com" target="_blank">百度一下</a>
//                tartget有4个属性：
//                _blank
//                浏览器总在一个新打开、未命名的窗口中载入目标文档。
//                _self
//                这个目标的值对所有没有指定目标的 <a> 标签是默认目标，它使得目标文档载入并显示在相同的框架或者窗口中作为源文档。这个目标是多余且不必要的，除非和文档标题 <base> 标签中的 target 属性一起使用。
//                _parent
//                这个目标使得文档载入父窗口或者包含来超链接引用的框架的框架集。如果这个引用是在窗口或者在顶级框架中，那么它与目标 _self 等效。
//                _top
//                这个目标使得文档载入包含这个超链接的窗口，用 _top 目标将会清除所有被包含的框架并将文档载入整个浏览器窗口。

//                参数说明:
//                view :请求新窗口的WebView
//                isDialog ： 如果是true，代表这个新窗口只是个对话框，如果是false，则是一个整体的大小的窗口
//                isUserGesture 如果是true，代表这个请求是用户触发的，例如点击一个页面上的一个连接
//                resultMsg ，当一个新的WebView被创建时这个只被传递给他，resultMsg.obj是一个WebViewTransport的对象，它被用来传送给新创建的WebView，使用方法：
//                WebView.WebViewTransport.setWebView(WebView)
//                返回值：这个方法如果返回true，代表这个主机应用会创建一个新的窗口，否则应该返回fasle。如果你返回了false，但是依然发送resulMsg会导致一个未知的结果。
            }
        });
    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private void setWebClient() {
        wb.setWebViewClient(new WebViewClient() {

            //页面有重定向，代码执行顺序:
            //pageStart(初始界面地址)----->overrideUrl(先解析到重定向的地址)----->pageStart(重定向界面地址)----->pageFinish
            //                                |                                    |
            //                                 ---------有多个重定向的话循环多次-------


            //页面没有重定向，代码执行顺序:
            //pageStart----->pageFinish
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                //不设置的话默认由ActivityManager选择如何加载webview，返回true表示
                //由手机选择打开方式，返回false表示由本webview加载url，默认super中返回
                //false。

                //POST方式不调用此方法,loadUrl才会调用此方法
                Log.d("pageOverrideUrlLoading", url);
//                if (Uri.parse(url).getHost().contains("baidu")) {
//                    return false;
//                }
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//                return true;
                return false;
//                return super.shouldOverrideUrlLoading(view,url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                //每次加载页面都会调用,当页面有重定向时可执行多次

                Log.d("pageStart", url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //内核不同,调用时机不相同,所以使用的时候需要小心

                Log.d("pageFinish", url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                //页面加载资源时调用,会显示所有资源的url
                Log.d("pageLoadResource", url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("pageInterceptRequest", url);

                //在pageStart前调用，加载资源的url，返回null的话就加载原地址资源
                //这个在其他线程中进行，如果对UI或其他在主线程中进行的代码需要注意

                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.d("pageError", failingUrl);
                //ERROR_* 错误
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                //HTTP错误码时调用，资源或者其他url加载错误时都会调用
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
                //处理SSL认证请求，在UI线程中进行，在回调完成前request会被挂起，默认是cancel，没有认证
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
                //接收http的认证请求，使用HttpAuthHandler去处理，默认是cancel，没有认证
            }
        });
    }

    private void setWebSettings() {
        WebSettings settings = wb.getSettings();
        settings.setAllowFileAccess(true);//启用或禁止WebView访问文件数据,默认允许，只对file有影响，对assets、res文件无影响
        settings.setBlockNetworkImage(false);// 是否禁止显示网络图像，默认false，受setBlockNetworkLoads和getLoadsImagesAutomatically影响
        settings.setBuiltInZoomControls(false);// 设置是否支持缩放，默认false
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 设置缓存的模式，默认LOAD_DEFAULT
        settings.setDefaultFontSize(20);// 设置默认的字体大小，默认16，1-72之间
        settings.setDefaultTextEncodingName("utf-8");// 设置在解码时使用的默认编码，默认UTF-8
        settings.setFixedFontFamily("monospace");// 设置固定使用的字体,默认monospace
        settings.setJavaScriptEnabled(true);// 设置是否支持Javascript，默认false  有时js中的动画会在后台一直运行,需要在onResume和onStop中设置true,false
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 设置布局方式,支持内容重新布局，默认Narrow
        settings.setSupportZoom(false);// 设置是否支持缩放，默认为true
        settings.setSaveFormData(true);// 是否保存数据，默认为true
        settings.setTextZoom(100);// 设置文字缩放比 默认100、
        settings.setUseWideViewPort(false);//将图片调整到适合webview的大小，false的话使用css中的width，true的话使用tag中的width
        settings.setSupportMultipleWindows(true);//多窗口,默认false,配合onCreateWindow使用
        settings.setNeedInitialFocus(true);//当webview调用requestFocus时为webview设置节点，默认true
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口，默认false，true的话js可通过window.open()打开窗口
        settings.setLoadWithOverviewMode(false);//缩放至屏幕的大小，默认false
        settings.setLoadsImagesAutomatically(true);//支持自动加载图片


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebIconDatabase.getInstance().close();
        ((LinearLayout) wb.getRootView()).removeView(wb);//调用destroy之前调用
        wb.destroy();
//        wb.removeJavascriptInterface();//为了安全,需要移除内部的一些jsInterface
        if(Build.VERSION.SDK_INT > 10 && Build.VERSION.SDK_INT < 17) {
            try {
                wb.removeJavascriptInterface("searchBoxJavaBridge_");
                wb.removeJavascriptInterface("accessibility");
                wb.removeJavascriptInterface("accessibilityTraversal");
            } catch (Throwable var8) {
                var8.printStackTrace();
            }
        }
    }


    //webview加载大网页的时候 容易造成内存泄露问题,解决方案两种:
    //1.开启单独的进程启动webview,退出时销毁进程,进程开启见activity中的process包下
    //2.反射销毁资源
    public void releaseAllWebViewCallback() {
        if (android.os.Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 网页更新cookie 设置完cookie以后 不刷新页面即可生效,webview的cookie存放在应用的databases里的webviewCookie的.db的cookies表中
     *
     * @param url
     * @param value
     */
    public void updateCookies(String url, String value) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) { // 2.3及以下
            CookieSyncManager.createInstance(getApplicationContext());
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, value);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();//强制刷新cookie
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            CookieSyncManager.getInstance().sync();
        }
    }


    /**
     * 处理返回键
     *
     * @param keyCoder
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (wb.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            wb.goBack(); //goBack()表示返回webView的上一页面
            return true;
        }
        return false;
    }


    /**
     * 清除缓存
     *
     * @param context
     */
    public void clearCaches(Context context) {
        File file = getCacheDir();
        if (file != null && file.exists() && file.isDirectory()) {
            for (File item : file.listFiles()) {
                item.delete();
            }
            file.delete();
        }
        CookieManager.getInstance().removeSessionCookie();
        wb.clearCache(true);
        wb.clearHistory();
        context.deleteDatabase("webview.db");
        context.deleteDatabase("webviewCache.db");
    }

    /**
     * 处理长按弹出的上下文菜单事件，包括网页链接，图片链接等...
     */
    class CustomContextMenuListener implements View.OnCreateContextMenuListener {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            WebView.HitTestResult result = ((WebView) v).getHitTestResult();//可以识别网页元素
            if (null == result)
                return;

            int type = result.getType();
            if (type == WebView.HitTestResult.UNKNOWN_TYPE)//返回null可判断是否为重定向
                return;

            if (type == WebView.HitTestResult.EDIT_TEXT_TYPE) {
                // let TextView handles context menu
                return;
            }

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.browsercontext, menu);

            // Show the correct menu group
            String extra = result.getExtra();
            menu.setGroupVisible(R.id.PHONE_MENU,
                    type == WebView.HitTestResult.PHONE_TYPE);
            menu.setGroupVisible(R.id.EMAIL_MENU,
                    type == WebView.HitTestResult.EMAIL_TYPE);
            menu.setGroupVisible(R.id.GEO_MENU,
                    type == WebView.HitTestResult.GEO_TYPE);
            menu.setGroupVisible(R.id.IMAGE_MENU,
                    type == WebView.HitTestResult.IMAGE_TYPE
                            || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE);
            menu.setGroupVisible(R.id.ANCHOR_MENU,
                    type == WebView.HitTestResult.SRC_ANCHOR_TYPE
                            || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE);

            // Setup custom handling depending on the type
            Intent intent;
            switch (type) {
                case WebView.HitTestResult.PHONE_TYPE:
                    menu.setHeaderTitle(Uri.decode(extra));
                    menu.findItem(R.id.dial_context_menu_id).setIntent(
                            new Intent(Intent.ACTION_VIEW, Uri
                                    .parse(WebView.SCHEME_TEL + extra)));
                    Intent addIntent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                    addIntent.putExtra(ContactsContract.Intents.Insert.PHONE, Uri.decode(extra));
                    addIntent.setType(Contacts.People.CONTENT_ITEM_TYPE);
                    menu.findItem(R.id.add_contact_context_menu_id).setIntent(
                            addIntent);
                    menu.findItem(R.id.copy_phone_context_menu_id).setOnMenuItemClickListener(
                            new Copy(extra));
                    break;

                case WebView.HitTestResult.EMAIL_TYPE:
                    menu.setHeaderTitle(extra);
                    menu.findItem(R.id.email_context_menu_id).setIntent(
                            new Intent(Intent.ACTION_VIEW, Uri
                                    .parse(WebView.SCHEME_MAILTO + extra)));
                    menu.findItem(R.id.copy_mail_context_menu_id).setOnMenuItemClickListener(
                            new Copy(extra));
                    break;

                case WebView.HitTestResult.GEO_TYPE:
                    menu.setHeaderTitle(extra);
                    menu.findItem(R.id.map_context_menu_id).setIntent(
                            new Intent(Intent.ACTION_VIEW, Uri
                                    .parse(WebView.SCHEME_GEO
                                            + URLEncoder.encode(extra))));
                    menu.findItem(R.id.copy_geo_context_menu_id).setOnMenuItemClickListener(
                            new Copy(extra));
                    break;

                case WebView.HitTestResult.SRC_ANCHOR_TYPE:
                case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                    TextView titleView = (TextView) LayoutInflater.from(getApplicationContext())
                            .inflate(android.R.layout.browser_link_context_header,
                                    null);
                    titleView.setText(extra);
                    menu.setHeaderView(titleView);
                    // decide whether to show the open link in new tab option
                    menu.findItem(R.id.open_newtab_context_menu_id).setVisible(
                            true);
                    PackageManager pm = getPackageManager();
                    Intent send = new Intent(Intent.ACTION_SEND);
                    send.setType("text/plain");
                    ResolveInfo ri = pm.resolveActivity(send, PackageManager.MATCH_DEFAULT_ONLY);
                    menu.findItem(R.id.share_link_context_menu_id).setVisible(ri != null);
                    if (type == WebView.HitTestResult.SRC_ANCHOR_TYPE) {
                        break;
                    }
                    // otherwise fall through to handle image part
                case WebView.HitTestResult.IMAGE_TYPE:
                    if (type == WebView.HitTestResult.IMAGE_TYPE) {
                        menu.setHeaderTitle(extra);
                    }
                    menu.findItem(R.id.view_image_context_menu_id).setIntent(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(extra)));
//                    menu.findItem(R.id.download_context_menu_id).
//                            setOnMenuItemClickListener();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 预加载资源策略
     */
    private void preLoadData(){
        //通过下载zip包加载网页资源,通过cache-control或者etag控制过期时间
    }

    private class Copy implements MenuItem.OnMenuItemClickListener {
        private CharSequence mText;

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(mText);
            return true;
        }

        public Copy(CharSequence toCopy) {
            mText = toCopy;
        }
    }

    /**
     * 判断是否到达底端
     *
     * @return
     */
    public boolean judgeScrollToBottom() {
//getScrollY()方法返回的是当前可见区域的顶端距整个页面顶端的距离,也就是当前内容滚动的距离.
//getHeight()或者getBottom()方法都返回当前WebView 这个容器的高度
//getContentHeight 返回的是整个html 的高度,但并不等同于当前整个页面的高度,因为WebView 有缩放功能, 所以当前整个页面的高度实际上应该是原始html 的高度再乘上缩放比例.
        if (wb.getContentHeight() * wb.getScale() == (wb.getHeight() + wb.getScrollY())) {
            //已经处于底端
            return true;
        }
        return false;
    }


    /**
     * 下载接口监听
     */
    public class CustomDownloadLsn implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}


@SuppressWarnings("deprecation")
class AdvancedWebView extends WebView {

    public interface Listener {
        void onPageStarted(String url, Bitmap favicon);

        void onPageFinished(String url);

        void onPageError(int errorCode, String description, String failingUrl);

        void onDownloadRequested(String url, String userAgent, String contentDisposition, String mimetype, long contentLength);

        void onExternalPageRequest(String url);
    }

    public static final String PACKAGE_NAME_DOWNLOAD_MANAGER = "com.android.providers.downloads";
    protected static final int REQUEST_CODE_FILE_PICKER = 51426;
    protected static final String DATABASES_SUB_FOLDER = "/databases";
    protected static final String LANGUAGE_DEFAULT_ISO3 = "eng";
    protected static final String CHARSET_DEFAULT = "UTF-8";
    /**
     * Alternative browsers that have their own rendering engine and *may* be installed on this device
     */
    protected static final String[] ALTERNATIVE_BROWSERS = new String[]{"org.mozilla.firefox", "com.android.chrome", "com.opera.browser", "org.mozilla.firefox_beta", "com.chrome.beta", "com.opera.browser.beta"};
    protected WeakReference<Activity> mActivity;
    protected WeakReference<Fragment> mFragment;
    protected Listener mListener;
    protected final List<String> mPermittedHostnames = new LinkedList<String>();
    /**
     * 文件上传回调5.0之前
     */
    protected ValueCallback<Uri> mFileUploadCallbackFirst;
    /**
     * 文件上传回调5.0之后
     */
    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;
    protected long mLastError;
    protected String mLanguageIso3;
    protected int mRequestCodeFilePicker = REQUEST_CODE_FILE_PICKER;
    protected WebViewClient mCustomWebViewClient;
    protected WebChromeClient mCustomWebChromeClient;
    protected boolean mGeolocationEnabled;
    protected String mUploadableFileTypes = "image/*";
    protected final Map<String, String> mHttpHeaders = new HashMap<String, String>();
    public String mTitle;

    public AdvancedWebView(Context context) {
        super(context);
        init(context);
    }

    public AdvancedWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AdvancedWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setListener(final Activity activity, final Listener listener) {
        setListener(activity, listener, REQUEST_CODE_FILE_PICKER);
    }

    public void setListener(final Activity activity, final Listener listener, final int requestCodeFilePicker) {
        if (activity != null) {
            mActivity = new WeakReference<Activity>(activity);
        } else {
            mActivity = null;
        }

        setListener(listener, requestCodeFilePicker);
    }

    public void setListener(final Fragment fragment, final Listener listener) {
        setListener(fragment, listener, REQUEST_CODE_FILE_PICKER);
    }

    public void setListener(final Fragment fragment, final Listener listener, final int requestCodeFilePicker) {
        if (fragment != null) {
            mFragment = new WeakReference<Fragment>(fragment);
        } else {
            mFragment = null;
        }

        setListener(listener, requestCodeFilePicker);
    }

    protected void setListener(final Listener listener, final int requestCodeFilePicker) {
        mListener = listener;
        mRequestCodeFilePicker = requestCodeFilePicker;
    }

    @Override
    public void setWebViewClient(final WebViewClient client) {
        mCustomWebViewClient = client;
    }

    @Override
    public void setWebChromeClient(final WebChromeClient client) {
        mCustomWebChromeClient = client;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setGeolocationEnabled(final boolean enabled) {
        if (enabled) {
            getSettings().setJavaScriptEnabled(true);
            getSettings().setGeolocationEnabled(true);
            setGeolocationDatabasePath();
        }

        mGeolocationEnabled = enabled;
    }

    @SuppressLint("NewApi")
    protected void setGeolocationDatabasePath() {
        final Activity activity;

        if (mFragment != null && mFragment.get() != null && Build.VERSION.SDK_INT >= 11 && mFragment.get().getActivity() != null) {
            activity = mFragment.get().getActivity();
        } else if (mActivity != null && mActivity.get() != null) {
            activity = mActivity.get();
        } else {
            return;
        }

        getSettings().setGeolocationDatabasePath(activity.getFilesDir().getPath());
    }

    public void setUploadableFileTypes(final String mimeType) {
        mUploadableFileTypes = mimeType;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("all")
    public void onResume() {
        if (Build.VERSION.SDK_INT >= 11) {
            super.onResume();
        }
        resumeTimers();
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("all")
    public void onPause() {
        pauseTimers();
        if (Build.VERSION.SDK_INT >= 11) {
            super.onPause();
        }
    }

    public void onDestroy() {
        try {
            ((ViewGroup) getParent()).removeView(this);
        } catch (Exception e) {
        }

        try {
            removeAllViews();
        } catch (Exception e) {
        }
        destroy();
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (requestCode == mRequestCodeFilePicker) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    if (mFileUploadCallbackFirst != null) {
                        mFileUploadCallbackFirst.onReceiveValue(intent.getData());
                        mFileUploadCallbackFirst = null;
                    } else if (mFileUploadCallbackSecond != null) {
                        Uri[] dataUris;
                        try {
                            dataUris = new Uri[]{Uri.parse(intent.getDataString())};
                        } catch (Exception e) {
                            dataUris = null;
                        }

                        mFileUploadCallbackSecond.onReceiveValue(dataUris);
                        mFileUploadCallbackSecond = null;
                    }
                }
            } else {
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(null);
                    mFileUploadCallbackFirst = null;
                } else if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond.onReceiveValue(null);
                    mFileUploadCallbackSecond = null;
                }
            }
        }
    }

    /**
     * Adds an additional HTTP header that will be sent along with every request
     * <p/>
     * If you later want to delete an HTTP header that was previously added this way, call `removeHttpHeader()`
     * <p/>
     * The `WebView` implementation may in some cases overwrite headers that you set or unset
     *
     * @param name  the name of the HTTP header to add
     * @param value the value of the HTTP header to send
     */
    public void addHttpHeader(final String name, final String value) {
        mHttpHeaders.put(name, value);
    }

    /**
     * Removes one of the HTTP headers that have previously been added via `addHttpHeader()`
     * <p/>
     * If you want to unset a pre-defined header, set it to an empty string with `addHttpHeader()` instead
     * <p/>
     * The `WebView` implementation may in some cases overwrite headers that you set or unset
     *
     * @param name the name of the HTTP header to remove
     */
    public void removeHttpHeader(final String name) {
        mHttpHeaders.remove(name);
    }

    public void addPermittedHostname(String hostname) {
        mPermittedHostnames.add(hostname);
    }

    public void addPermittedHostnames(Collection<? extends String> collection) {
        mPermittedHostnames.addAll(collection);
    }

    public List<String> getPermittedHostnames() {
        return mPermittedHostnames;
    }

    public void removePermittedHostname(String hostname) {
        mPermittedHostnames.remove(hostname);
    }

    public void clearPermittedHostnames() {
        mPermittedHostnames.clear();
    }

    public boolean onBackPressed() {
        if (canGoBack()) {
            goBack();
            return false;
        } else {
            return true;
        }
    }

    @SuppressLint("NewApi")
    protected static void setAllowAccessFromFileUrls(final WebSettings webSettings, final boolean allowed) {
        if (Build.VERSION.SDK_INT >= 16) {
            webSettings.setAllowFileAccessFromFileURLs(allowed);
            webSettings.setAllowUniversalAccessFromFileURLs(allowed);
        }
    }

    @SuppressWarnings("static-method")
    public void setCookiesEnabled(final boolean enabled) {
        CookieManager.getInstance().setAcceptCookie(enabled);
    }

    @SuppressLint("NewApi")
    public void setThirdPartyCookiesEnabled(final boolean enabled) {
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, enabled);
        }
    }

    public void setMixedContentAllowed(final boolean allowed) {
        setMixedContentAllowed(getSettings(), allowed);
    }

    @SuppressWarnings("static-method")
    @SuppressLint("NewApi")
    protected void setMixedContentAllowed(final WebSettings webSettings, final boolean allowed) {
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(allowed ? WebSettings.MIXED_CONTENT_ALWAYS_ALLOW : WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    protected void init(final Context context) {
        if (context instanceof Activity) {
            mActivity = new WeakReference<Activity>((Activity) context);
        }

        mLanguageIso3 = getLanguageIso3();

        setFocusable(true);
        setFocusableInTouchMode(true);

        setSaveEnabled(true);

        final String filesDir = context.getFilesDir().getPath();
        final String databaseDir = filesDir.substring(0, filesDir.lastIndexOf("/")) + DATABASES_SUB_FOLDER;

        final WebSettings webSettings = getSettings();
        webSettings.setAllowFileAccess(false);
        setAllowAccessFromFileUrls(webSettings, false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT < 18) {
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        }
        webSettings.setDatabaseEnabled(true);
        if (Build.VERSION.SDK_INT < 19) {
            webSettings.setDatabasePath(databaseDir);
        }
        setMixedContentAllowed(webSettings, true);

        setThirdPartyCookiesEnabled(true);

        super.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!hasError()) {
                    if (mListener != null) {
                        mListener.onPageStarted(url, favicon);
                    }
                }

                if (mCustomWebViewClient != null) {
                    mCustomWebViewClient.onPageStarted(view, url, favicon);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!hasError()) {
                    if (mListener != null) {
                        mListener.onPageFinished(url);
                    }
                }

                if (mCustomWebViewClient != null) {
                    mCustomWebViewClient.onPageFinished(view, url);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                setLastError();

                if (mListener != null) {
                    mListener.onPageError(errorCode, description, failingUrl);
                }

                if (mCustomWebViewClient != null) {
                    mCustomWebViewClient.onReceivedError(view, errorCode, description, failingUrl);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (isHostnameAllowed(url)) {
                    if (mCustomWebViewClient != null) {
                        return mCustomWebViewClient.shouldOverrideUrlLoading(view, url);
                    } else {
                        return false;
                    }
                } else {
                    if (mListener != null) {
                        mListener.onExternalPageRequest(url);
                    }

                    return true;
                }
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                if (mCustomWebViewClient != null) {
                    mCustomWebViewClient.onLoadResource(view, url);
                } else {
                    super.onLoadResource(view, url);
                }
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("all")
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (Build.VERSION.SDK_INT >= 11) {
                    if (mCustomWebViewClient != null) {
                        return mCustomWebViewClient.shouldInterceptRequest(view, url);
                    } else {
                        return super.shouldInterceptRequest(view, url);
                    }
                } else {
                    return null;
                }
            }

            /**
             *
             * 从asset目录中加载资源
             * create by guoyizhe
             */
            private WebResourceResponse editResponse(String fileName){
                try {
                    return new WebResourceResponse("application/x-javascript","utf-8",context.getAssets().open(fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("all")
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (mCustomWebViewClient != null) {
                        return mCustomWebViewClient.shouldInterceptRequest(view, request);
                    } else {
                        return super.shouldInterceptRequest(view, request);
                    }
                } else {
                    return null;
                }
            }

            @Override
            public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                if (mCustomWebViewClient != null) {
                    mCustomWebViewClient.onFormResubmission(view, dontResend, resend);
                } else {
                    super.onFormResubmission(view, dontResend, resend);
                }
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                if (mCustomWebViewClient != null) {
                    mCustomWebViewClient.doUpdateVisitedHistory(view, url, isReload);
                } else {
                    super.doUpdateVisitedHistory(view, url, isReload);
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (mCustomWebViewClient != null) {
                    mCustomWebViewClient.onReceivedSslError(view, handler, error);
                } else {
                    super.onReceivedSslError(view, handler, error);
                }
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("all")
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (mCustomWebViewClient != null) {
                        mCustomWebViewClient.onReceivedClientCertRequest(view, request);
                    } else {
                        super.onReceivedClientCertRequest(view, request);
                    }
                }
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                if (mCustomWebViewClient != null) {
                    mCustomWebViewClient.onReceivedHttpAuthRequest(view, handler, host, realm);
                } else {
                    super.onReceivedHttpAuthRequest(view, handler, host, realm);
                }
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                if (mCustomWebViewClient != null) {
                    return mCustomWebViewClient.shouldOverrideKeyEvent(view, event);
                } else {
                    return super.shouldOverrideKeyEvent(view, event);
                }
            }

            @Override
            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                if (mCustomWebViewClient != null) {
                    mCustomWebViewClient.onUnhandledKeyEvent(view, event);
                } else {
                    super.onUnhandledKeyEvent(view, event);
                }
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("all")
            public void onUnhandledInputEvent(WebView view, InputEvent event) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (mCustomWebViewClient != null) {
//                        mCustomWebViewClient.onUnhandledInputEvent(view, event);
                    } else {
//                        super.onUnhandledInputEvent(view, event);
                    }
                }
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                if (mCustomWebViewClient != null) {
                    mCustomWebViewClient.onScaleChanged(view, oldScale, newScale);
                } else {
                    super.onScaleChanged(view, oldScale, newScale);
                }
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("all")
            public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
                if (Build.VERSION.SDK_INT >= 12) {
                    if (mCustomWebViewClient != null) {
                        mCustomWebViewClient.onReceivedLoginRequest(view, realm, account, args);
                    } else {
                        super.onReceivedLoginRequest(view, realm, account, args);
                    }
                }
            }

        });

        super.setWebChromeClient(new WebChromeClient() {

            // file upload callback (Android 2.2 (API level 8) -- Android 2.3 (API level 10)) (hidden method)
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, null);
            }

            // file upload callback (Android 3.0 (API level 11) -- Android 4.0 (API level 15)) (hidden method)
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooser(uploadMsg, acceptType, null);
            }

            // file upload callback (Android 4.1 (API level 16) -- Android 4.3 (API level 18)) (hidden method)
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileInput(uploadMsg, null);
            }

            // file upload callback (Android 5.0 (API level 21) -- current) (public method)
            @SuppressWarnings("all")
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                openFileInput(null, filePathCallback);
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onProgressChanged(view, newProgress);
                } else {
                    super.onProgressChanged(view, newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onReceivedTitle(view, title);
                    mTitle = title;
                } else {
                    super.onReceivedTitle(view, title);
                    mTitle = title;
                }
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onReceivedIcon(view, icon);
                } else {
                    super.onReceivedIcon(view, icon);
                }
            }

            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onReceivedTouchIconUrl(view, url, precomposed);
                } else {
                    super.onReceivedTouchIconUrl(view, url, precomposed);
                }
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onShowCustomView(view, callback);
                } else {
                    super.onShowCustomView(view, callback);
                }
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("all")
            public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
                if (Build.VERSION.SDK_INT >= 14) {
                    if (mCustomWebChromeClient != null) {
                        mCustomWebChromeClient.onShowCustomView(view, requestedOrientation, callback);
                    } else {
                        super.onShowCustomView(view, requestedOrientation, callback);
                    }
                }
            }

            @Override
            public void onHideCustomView() {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onHideCustomView();
                } else {
                    super.onHideCustomView();
                }
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                if (mCustomWebChromeClient != null) {
                    return mCustomWebChromeClient.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
                } else {
                    return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
                }
            }

            @Override
            public void onRequestFocus(WebView view) {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onRequestFocus(view);
                } else {
                    super.onRequestFocus(view);
                }
            }

            @Override
            public void onCloseWindow(WebView window) {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onCloseWindow(window);
                } else {
                    super.onCloseWindow(window);
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                if (mCustomWebChromeClient != null) {
                    return mCustomWebChromeClient.onJsAlert(view, url, message, result);
                } else {
                    return super.onJsAlert(view, url, message, result);
                }
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                if (mCustomWebChromeClient != null) {
                    return mCustomWebChromeClient.onJsConfirm(view, url, message, result);
                } else {
                    return super.onJsConfirm(view, url, message, result);
                }
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                if (mCustomWebChromeClient != null) {
                    return mCustomWebChromeClient.onJsPrompt(view, url, message, defaultValue, result);
                } else {
                    return super.onJsPrompt(view, url, message, defaultValue, result);
                }
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                if (mCustomWebChromeClient != null) {
                    return mCustomWebChromeClient.onJsBeforeUnload(view, url, message, result);
                } else {
                    return super.onJsBeforeUnload(view, url, message, result);
                }
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                if (mGeolocationEnabled) {
                    callback.invoke(origin, true, false);
                } else {
                    if (mCustomWebChromeClient != null) {
                        mCustomWebChromeClient.onGeolocationPermissionsShowPrompt(origin, callback);
                    } else {
                        super.onGeolocationPermissionsShowPrompt(origin, callback);
                    }
                }
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onGeolocationPermissionsHidePrompt();
                } else {
                    super.onGeolocationPermissionsHidePrompt();
                }
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("all")
            public void onPermissionRequest(PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (mCustomWebChromeClient != null) {
                        mCustomWebChromeClient.onPermissionRequest(request);
                    } else {
                        super.onPermissionRequest(request);
                    }
                }
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("all")
            public void onPermissionRequestCanceled(PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (mCustomWebChromeClient != null) {
                        mCustomWebChromeClient.onPermissionRequestCanceled(request);
                    } else {
                        super.onPermissionRequestCanceled(request);
                    }
                }
            }

            @Override
            public boolean onJsTimeout() {
                if (mCustomWebChromeClient != null) {
                    return mCustomWebChromeClient.onJsTimeout();
                } else {
                    return super.onJsTimeout();
                }
            }

            @Override
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onConsoleMessage(message, lineNumber, sourceID);
                } else {
                    super.onConsoleMessage(message, lineNumber, sourceID);
                }
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (mCustomWebChromeClient != null) {
                    return mCustomWebChromeClient.onConsoleMessage(consoleMessage);
                } else {
                    return super.onConsoleMessage(consoleMessage);
                }
            }

            @Override
            public Bitmap getDefaultVideoPoster() {
                if (mCustomWebChromeClient != null) {
                    return mCustomWebChromeClient.getDefaultVideoPoster();
                } else {
                    return super.getDefaultVideoPoster();
                }
            }

            @Override
            public View getVideoLoadingProgressView() {
                if (mCustomWebChromeClient != null) {
                    return mCustomWebChromeClient.getVideoLoadingProgressView();
                } else {
                    return super.getVideoLoadingProgressView();
                }
            }

            @Override
            public void getVisitedHistory(ValueCallback<String[]> callback) {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.getVisitedHistory(callback);
                } else {
                    super.getVisitedHistory(callback);
                }
            }

            @Override
            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
                } else {
                    super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
                }
            }

            @Override
            public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
                if (mCustomWebChromeClient != null) {
                    mCustomWebChromeClient.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
                } else {
                    super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
                }
            }

        });

        setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (mListener != null) {
                    mListener.onDownloadRequested(url, userAgent, contentDisposition, mimetype, contentLength);
                }
            }

        });
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t == 0) {
            //到达webview顶部,可以禁止下拉刷新
        }else{

        }
    }

    @Override
    public void loadUrl(final String url, Map<String, String> additionalHttpHeaders) {
        if (additionalHttpHeaders == null) {
            additionalHttpHeaders = mHttpHeaders;
        } else if (mHttpHeaders.size() > 0) {
            additionalHttpHeaders.putAll(mHttpHeaders);
        }

        super.loadUrl(url, additionalHttpHeaders);
    }

    @Override
    public void loadUrl(final String url) {
        if (mHttpHeaders.size() > 0) {
            super.loadUrl(url, mHttpHeaders);
        } else {
            super.loadUrl(url);
        }
    }

    public void loadUrl(String url, final boolean preventCaching) {
        if (preventCaching) {
            url = makeUrlUnique(url);
        }

        loadUrl(url);
    }

    public void loadUrl(String url, final boolean preventCaching, final Map<String, String> additionalHttpHeaders) {
        if (preventCaching) {
            url = makeUrlUnique(url);
        }

        loadUrl(url, additionalHttpHeaders);
    }

    protected static String makeUrlUnique(final String url) {
        StringBuilder unique = new StringBuilder();
        unique.append(url);

        if (url.contains("?")) {
            unique.append('&');
        } else {
            if (url.lastIndexOf('/') <= 7) {
                unique.append('/');
            }
            unique.append('?');
        }

        unique.append(System.currentTimeMillis());
        unique.append('=');
        unique.append(1);

        return unique.toString();
    }

    protected boolean isHostnameAllowed(String url) {
        if (mPermittedHostnames.size() == 0) {
            return true;
        }

        url = url.replace("http://", "");
        url = url.replace("https://", "");

        for (String hostname : mPermittedHostnames) {
            if (url.startsWith(hostname)) {
                return true;
            }
        }

        return false;
    }

    protected void setLastError() {
        mLastError = System.currentTimeMillis();
    }

    protected boolean hasError() {
        return (mLastError + 500) >= System.currentTimeMillis();
    }

    protected static String getLanguageIso3() {
        try {
            return Locale.getDefault().getISO3Language().toLowerCase(Locale.US);
        } catch (MissingResourceException e) {
            return LANGUAGE_DEFAULT_ISO3;
        }
    }

    /**
     * Provides localizations for the 25 most widely spoken languages that have a ISO 639-2/T code
     */
    protected String getFileUploadPromptLabel() {
        try {
            if (mLanguageIso3.equals("zho"))
                return decodeBase64("6YCJ5oup5LiA5Liq5paH5Lu2");
            else if (mLanguageIso3.equals("spa"))
                return decodeBase64("RWxpamEgdW4gYXJjaGl2bw==");
            else if (mLanguageIso3.equals("hin"))
                return decodeBase64("4KSP4KSVIOCkq+CkvOCkvuCkh+CksiDgpJrgpYHgpKjgpYfgpII=");
            else if (mLanguageIso3.equals("ben"))
                return decodeBase64("4KaP4KaV4Kaf4Ka/IOCmq+CmvuCmh+CmsiDgpqjgpr/gprDgp43gpqzgpr7gpprgpqg=");
            else if (mLanguageIso3.equals("ara"))
                return decodeBase64("2KfYrtiq2YrYp9ixINmF2YTZgSDZiNin2K3Yrw==");
            else if (mLanguageIso3.equals("por"))
                return decodeBase64("RXNjb2xoYSB1bSBhcnF1aXZv");
            else if (mLanguageIso3.equals("rus"))
                return decodeBase64("0JLRi9Cx0LXRgNC40YLQtSDQvtC00LjQvSDRhNCw0LnQuw==");
            else if (mLanguageIso3.equals("jpn"))
                return decodeBase64("MeODleOCoeOCpOODq+OCkumBuOaKnuOBl+OBpuOBj+OBoOOBleOBhA==");
            else if (mLanguageIso3.equals("pan"))
                return decodeBase64("4KiH4Kmx4KiVIOCoq+CovuCoh+CosiDgqJrgqYHgqKPgqYs=");
            else if (mLanguageIso3.equals("deu"))
                return decodeBase64("V8OkaGxlIGVpbmUgRGF0ZWk=");
            else if (mLanguageIso3.equals("jav"))
                return decodeBase64("UGlsaWggc2lqaSBiZXJrYXM=");
            else if (mLanguageIso3.equals("msa"))
                return decodeBase64("UGlsaWggc2F0dSBmYWls");
            else if (mLanguageIso3.equals("tel"))
                return decodeBase64("4LCS4LCVIOCwq+CxhuCxluCwsuCxjeCwqOCxgSDgsI7gsILgsJrgsYHgsJXgsYvgsILgsKHgsL8=");
            else if (mLanguageIso3.equals("vie"))
                return decodeBase64("Q2jhu41uIG3hu5l0IHThuq1wIHRpbg==");
            else if (mLanguageIso3.equals("kor"))
                return decodeBase64("7ZWY64KY7J2YIO2MjOydvOydhCDshKDtg50=");
            else if (mLanguageIso3.equals("fra"))
                return decodeBase64("Q2hvaXNpc3NleiB1biBmaWNoaWVy");
            else if (mLanguageIso3.equals("mar"))
                return decodeBase64("4KSr4KS+4KSH4KSyIOCkqOCkv+CkteCkoeCkvg==");
            else if (mLanguageIso3.equals("tam"))
                return decodeBase64("4K6S4K6w4K+BIOCuleCvh+CuvuCuquCvjeCuquCviCDgrqTgr4fgrrDgr43grrXgr4E=");
            else if (mLanguageIso3.equals("urd"))
                return decodeBase64("2KfbjNqpINmB2KfYptmEINmF24zauiDYs9uSINin2YbYqtiu2KfYqCDaqdix24zaug==");
            else if (mLanguageIso3.equals("fas"))
                return decodeBase64("2LHYpyDYp9mG2KrYrtin2Kgg2qnZhtuM2K8g24zaqSDZgdin24zZhA==");
            else if (mLanguageIso3.equals("tur"))
                return decodeBase64("QmlyIGRvc3lhIHNlw6dpbg==");
            else if (mLanguageIso3.equals("ita"))
                return decodeBase64("U2NlZ2xpIHVuIGZpbGU=");
            else if (mLanguageIso3.equals("tha"))
                return decodeBase64("4LmA4Lil4Li34Lit4LiB4LmE4Lif4Lil4LmM4Lir4LiZ4Li24LmI4LiH");
            else if (mLanguageIso3.equals("guj"))
                return decodeBase64("4KqP4KqVIOCqq+CqvuCqh+CqsuCqqOCrhyDgqqrgqrjgqoLgqqY=");
        } catch (Exception e) {
        }

        // return English translation by default
        return "Choose a file";
    }

    protected static String decodeBase64(final String base64) throws IllegalArgumentException, UnsupportedEncodingException {
        final byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        return new String(bytes, CHARSET_DEFAULT);
    }

    @SuppressLint("NewApi")
    protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst, final ValueCallback<Uri[]> fileUploadCallbackSecond) {
        if (mFileUploadCallbackFirst != null) {
            mFileUploadCallbackFirst.onReceiveValue(null);
        }
        mFileUploadCallbackFirst = fileUploadCallbackFirst;

        if (mFileUploadCallbackSecond != null) {
            mFileUploadCallbackSecond.onReceiveValue(null);
        }
        mFileUploadCallbackSecond = fileUploadCallbackSecond;

        Intent i = new Intent(Intent.ACTION_PICK);
//        i.addCategory(Intent.CATEGORY_APP_GALLERY);
        i.setType(mUploadableFileTypes);

        if (mFragment != null && mFragment.get() != null && Build.VERSION.SDK_INT >= 11) {
            mFragment.get().startActivityForResult(Intent.createChooser(i, getFileUploadPromptLabel()), mRequestCodeFilePicker);
        } else if (mActivity != null && mActivity.get() != null) {
            mActivity.get().startActivityForResult(Intent.createChooser(i, getFileUploadPromptLabel()), mRequestCodeFilePicker);
        }
    }

    /**
     * Returns whether file uploads can be used on the current device (generally all platform versions except for 4.4)
     *
     * @return whether file uploads can be used
     */
    public static boolean isFileUploadAvailable() {
        return isFileUploadAvailable(false);
    }

    /**
     * Returns whether file uploads can be used on the current device (generally all platform versions except for 4.4)
     * <p/>
     * On Android 4.4.3/4.4.4, file uploads may be possible but will come with a wrong MIME type
     *
     * @param needsCorrectMimeType whether a correct MIME type is required for file uploads or `application/octet-stream` is acceptable
     * @return whether file uploads can be used
     */
    public static boolean isFileUploadAvailable(final boolean needsCorrectMimeType) {
        if (Build.VERSION.SDK_INT == 19) {
            final String platformVersion = (Build.VERSION.RELEASE == null) ? "" : Build.VERSION.RELEASE;

            return !needsCorrectMimeType && (platformVersion.startsWith("4.4.3") || platformVersion.startsWith("4.4.4"));
        } else {
            return true;
        }
    }

    /**
     * Handles a download by loading the file from `fromUrl` and saving it to `toFilename` on the external storage
     * <p/>
     * This requires the two permissions `android.permission.INTERNET` and `android.permission.WRITE_EXTERNAL_STORAGE`
     * <p/>
     * Only supported on API level 9 (Android 2.3) and above
     *
     * @param context    a valid `Context` reference
     * @param fromUrl    the URL of the file to download, e.g. the one from `AdvancedWebView.onDownloadRequested(...)`
     * @param toFilename the name of the destination file where the download should be saved, e.g. `myImage.jpg`
     * @return whether the download has been successfully handled or not
     */
    @SuppressLint("NewApi")
    public static boolean handleDownload(final Context context, final String fromUrl, final String toFilename) {
        if (Build.VERSION.SDK_INT < 9) {
            throw new RuntimeException("Method requires API level 9 or above");
        }

        final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fromUrl));
        if (Build.VERSION.SDK_INT >= 11) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, toFilename);

        final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            try {
                dm.enqueue(request);
            } catch (SecurityException e) {
                if (Build.VERSION.SDK_INT >= 11) {
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                }
                dm.enqueue(request);
            }

            return true;
        }
        // if the download manager app has been disabled on the device
        catch (IllegalArgumentException e) {
            // show the settings screen where the user can enable the download manager app again
            openAppSettings(context, AdvancedWebView.PACKAGE_NAME_DOWNLOAD_MANAGER);

            return false;
        }
    }

    @SuppressLint("NewApi")
    private static boolean openAppSettings(final Context context, final String packageName) {
        if (Build.VERSION.SDK_INT < 9) {
            throw new RuntimeException("Method requires API level 9 or above");
        }

        try {
            final Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wrapper for methods related to alternative browsers that have their own rendering engines
     */
    public static class Browsers {

        /**
         * Package name of an alternative browser that is installed on this device
         */
        private static String mAlternativePackage;

        /**
         * Returns whether there is an alternative browser with its own rendering engine currently installed
         *
         * @param context a valid `Context` reference
         * @return whether there is an alternative browser or not
         */
        public static boolean hasAlternative(final Context context) {
            return getAlternative(context) != null;
        }

        /**
         * Returns the package name of an alternative browser with its own rendering engine or `null`
         *
         * @param context a valid `Context` reference
         * @return the package name or `null`
         */
        public static String getAlternative(final Context context) {
            if (mAlternativePackage != null) {
                return mAlternativePackage;
            }

            final List<String> alternativeBrowsers = Arrays.asList(ALTERNATIVE_BROWSERS);
            final List<ApplicationInfo> apps = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);

            for (ApplicationInfo app : apps) {
                if (!app.enabled) {
                    continue;
                }

                if (alternativeBrowsers.contains(app.packageName)) {
                    mAlternativePackage = app.packageName;

                    return app.packageName;
                }
            }

            return null;
        }

        /**
         * Opens the given URL in an alternative browser
         *
         * @param context a valid `Activity` reference
         * @param url     the URL to open
         */
        public static void openUrl(final Activity context, final String url) {
            openUrl(context, url, false);
        }

        /**
         * Opens the given URL in an alternative browser
         *
         * @param context           a valid `Activity` reference
         * @param url               the URL to open
         * @param withoutTransition whether to switch to the browser `Activity` without a transition
         */
        public static void openUrl(final Activity context, final String url, final boolean withoutTransition) {
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setPackage(getAlternative(context));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

            if (withoutTransition) {
                context.overridePendingTransition(0, 0);
            }
        }

    }
}

/**
 * 一个Fragment与webview结合使用正常保存状态的伪代码
 */

/**

class FragmentWithWebview extends Fragment {
//            WebView的状态保存和恢复不像其他原生View一样是自动完成的.
//            WebView不是继承自View的.
//            如果我们把WebView放在布局里, 不加处理, 那么Activity或Fragment重建的过程中, WebView的状态就会丢失, 变成初始状态.

//    但是Fragment还有另一种情况, 即Fragment被压入back stack, 此时它没有被destroy(), 所以没有调用onSavedInstanceState()这个方法.
//    这种情况返回的时候, 会从onCreateView()开始, 并且savedInstanceState为null, 于是其中WebView之前的状态在此时丢失了.
//    解决这种情况可以利用Fragment实例并未销毁的条件, 增加一个成员变量bundle, 保存WebView的状态

    private Bundle webViewState;
    private WebView webView;
    String TEST_URL;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (webViewState != null) {
            //Fragment实例并未被销毁, 重新create view
            webView.restoreState(webViewState);
        } else if (savedInstanceState != null) {
            //Fragment实例被销毁重建
            webView.restoreState(savedInstanceState);
        } else {
            //全新Fragment
            webView.loadUrl(TEST_URL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();

        //Fragment不被销毁(Fragment被加入back stack)的情况下, 依靠Fragment中的成员变量保存WebView状态
        webViewState = new Bundle();
        webView.saveState(webViewState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Fragment被销毁的情况, 依靠outState保存WebView状态
        if (webView != null) {
            webView.saveState(outState);
        }
    }
}
*/
