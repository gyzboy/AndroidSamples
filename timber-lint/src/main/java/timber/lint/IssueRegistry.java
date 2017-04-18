package timber.lint;

import com.android.tools.lint.detector.api.Issue;

import java.util.Arrays;
import java.util.List;

public final class IssueRegistry extends com.android.tools.lint.client.api.IssueRegistry {
  @Override public List<Issue> getIssues() {
    return Arrays.asList(WrongTimberUsageDetector.getIssues());

    //LogUsageDetector.ISSUE：用于检查不允许直接使用 Log.* 方式输出本地日志的代码
    //ToastUsageDetector.ISSUE：用于检查直接用 Toast 方式显示 toast 的代码
    //ActivitySuperClassDetector.ACTIVITY_SUPER_CLASS_ISSUE：用于检查 Activity 的基类
    //BuildGradleVersionDetector.ISSUE：用于检查 gradle 文件中不允许直接写数字版本号的代码
  }
}
