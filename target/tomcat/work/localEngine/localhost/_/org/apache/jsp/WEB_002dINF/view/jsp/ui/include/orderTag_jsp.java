package org.apache.jsp.WEB_002dINF.view.jsp.ui.include;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class orderTag_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<div class=\"right-title clearfix\">\r\n");
      out.write("\t<a>我的订单</a>\r\n");
      out.write("\t<ul class=\"order-nav fr clearfix\">\r\n");
      out.write("\t\t<li ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${tag==1?\"class=\\\"on\\\"\":\"\" }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("><a href=\"/u/order/list.html\">全部订单</a></li>\r\n");
      out.write("\t\t<li ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${tag==10?\"class=\\\"on\\\"\":\"\" }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("><a href=\"/u/order/list/obligation.html\">未付款</a></li>\r\n");
      out.write("\t\t<li\t");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${tag==20?\"class=\\\"on\\\"\":\"\" }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("><a href=\"/u/order/list/receiving.html\">交易中</a></li>\r\n");
      out.write("\t\t<li ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${tag==50?\"class=\\\"on\\\"\":\"\" }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("><a href=\"/u/order/list/completed.html\">交易完成</a></li>\r\n");
      out.write("\t\t<li ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${tag==60?\"class=\\\"on\\\"\":\"\" }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("><a href=\"/u/order/list/closed.html\">已关闭</a></li>\r\n");
      out.write("\t</ul>\r\n");
      out.write("</div>");
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
