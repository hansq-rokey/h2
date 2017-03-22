package org.apache.jsp.WEB_002dINF.view.jsp.ui.include;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class left_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");

String checkLefr = request.getParameter("checkLefr");

      out.write("\r\n");
      out.write("<input type=\"hidden\" id=\"checkLefr\" value=\"");
      out.print(checkLefr );
      out.write("\"/>\r\n");
      out.write("<h3>个人中心</h3>\r\n");
      out.write("<ul>\r\n");
      out.write("\t<li id=\"orderli\"><a href=\"/u/order/list.html\">我的订单</a></li>\r\n");
      out.write("\t<li id=\"codeli\" style=\"display:none\"><a href=\"/u/deposit/list.html\">定金</a></li>\r\n");
      out.write("</ul>\r\n");
      out.write("<h3 style=\"display:none\">售后服务</h3>\r\n");
      out.write("<ul>\r\n");
      out.write("\t<li id=\"thli\" style=\"display:none\"><a href=\"/u/service/list/back.html\">退货单</a></li>\r\n");
      out.write("\t<li id=\"hhli\" style=\"display:none\"><a href=\"/u/service/list/exchange.html\">换货单</a></li>\r\n");
      out.write("\t<!-- li id=\"wxli\"><a href=\"/u/service/list/repair.html\">维修单</a></li> -->\r\n");
      out.write("</ul>\r\n");
      out.write("<h3>账户管理</h3>\r\n");
      out.write("<ul>\r\n");
      out.write("\t<li id=\"addressli\"><a href=\"/u/address/list.html\">收货地址</a></li>\r\n");
      out.write("\t<li id=\"pwdli\"><a href=\"/u/user/updatepwd.html\">密码管理</a></li>\r\n");
      out.write("\t<!-- <li id=\"accountli\"><a href=\"/u/user/account.html\">账号管理</a></li> -->\r\n");
      out.write("</ul>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar leftOn = $(\"#checkLefr\").val();\r\n");
      out.write("\tif(leftOn == \"order\"){\r\n");
      out.write("\t\t$(\"#orderli\").attr(\"class\",\"on\");\r\n");
      out.write("\t}\r\n");
      out.write("\tif(leftOn == \"invite\"){\r\n");
      out.write("\t\t$(\"#codeli\").attr(\"class\",\"on\");\r\n");
      out.write("\t}\r\n");
      out.write("\tif(leftOn == \"th\"){\r\n");
      out.write("\t\t$(\"#thli\").attr(\"class\",\"on\");\r\n");
      out.write("\t}\r\n");
      out.write("\tif(leftOn == \"hh\"){\r\n");
      out.write("\t\t$(\"#hhli\").attr(\"class\",\"on\");\r\n");
      out.write("\t}\r\n");
      out.write("\tif(leftOn == \"wx\"){\r\n");
      out.write("\t\t$(\"#wxli\").attr(\"class\",\"on\");\r\n");
      out.write("\t}\r\n");
      out.write("\tif(leftOn == \"address\"){\r\n");
      out.write("\t\t$(\"#addressli\").attr(\"class\",\"on\");\r\n");
      out.write("\t}\r\n");
      out.write("\tif(leftOn == \"pwd\"){\r\n");
      out.write("\t\t$(\"#pwdli\").attr(\"class\",\"on\");\r\n");
      out.write("\t}\r\n");
      out.write("\tif(leftOn == \"account\"){\r\n");
      out.write("\t\t$(\"#accountli\").attr(\"class\",\"on\");\r\n");
      out.write("\t}\r\n");
      out.write("</script>");
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
