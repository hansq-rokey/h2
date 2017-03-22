package org.apache.jsp.WEB_002dINF.view.jsp.ui.include;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class footer_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write(" <meta charset=\"UTF-8\">\r\n");
      out.write("<footer>\r\n");
      out.write("    <div class=\"row footor\">\r\n");
      out.write("        <a href=\"/about.html\">关于白熊</a>|<a href=\"/join.html\">工作机会</a>|<a href=\"/contact.html\">联系我们</a>|<a target=\"_blank\" href=\"http://user.ibaixiong.com/agreement.html\">法律声明</a>|<a target=\"_blank\" href=\"http://wpa.qq.com/msgrd?v=3&uin=2175775986&site=qq&menu=yes\" style=\"\"/>在线客服</a></a>|<a><span class=\"space\"></span>客服热线  400-157-0088</a>\r\n");
      out.write("        <p style=\"color:#999;\">2015 Hangzhou Baixiong Technology Co.Ltd。ALLrights reserver. 备案号：浙ICP备15024007号-1. <a target=\"_blank\" href=\"http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010402000428\" style=\"display:inline-block;text-decoration:none;height:20px;line-height:20px;\"><img src=\"http://fe.ibaixiong.com/common/image/ghs.png\" style=\"float:left;\"/>浙公网安备 33010402000428号</a></p>\r\n");
      out.write("    </div>\r\n");
      out.write("</footer>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
