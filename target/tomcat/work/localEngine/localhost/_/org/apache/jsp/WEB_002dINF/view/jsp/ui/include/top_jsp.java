package org.apache.jsp.WEB_002dINF.view.jsp.ui.include;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class top_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<link rel=\"icon\" href=\"http://fe.ibaixiong.com/shop/image/xLogin.ico\"\tmce_href=\"http://fe.ibaixiong.com/shop/image/xLogin.ico\" type=\"image/x-icon\">\r\n");
      out.write("<div class=\"head clearfix\">\r\n");
      out.write("\t<div class=\"logo\"><a href=\"###\"><img src=\"http://fe.ibaixiong.com/common/image/logo02.png\" class=\"indexlogo\" /></a></div>\r\n");
      out.write("\t\r\n");
      out.write("\t<ul class=\"login clearfix\">\r\n");
      out.write("\t\t<li id=\"loginLi\"><a href=\"http://login.ibaixiong.com/?service=http://daili.ibaixiong.com/u/order/list.html\">登录/注册</a></li>\r\n");
      out.write("\t\t<li id=\"infoLi\" style=\"display: none;\"><a href=\"/u/order/list.html\" id=\"userInfo\">个人中心<img src=\"http://fe.ibaixiong.com/shop/images/jiantou.png\" style=\"margin-left:5px;\"></a>\r\n");
      out.write("\t\t\t\t<span class=\"sanjiao\"></span>\r\n");
      out.write("\t\t\t\t<div class=\"person\">\r\n");
      out.write("\t\t\t\t\t<p><a href=\"/u/order/list.html\" id=\"userName\" style=\"width:118px;text-overflow: ellipsis;white-space:nowrap;overflow:hidden;display:inline-block\"></a></p>\r\n");
      out.write("\t\t\t\t\t<p><a href=\"/u/order/list.html\">我的订单</a></p>\r\n");
      out.write("\t\t\t\t\t<p><a href=\"/u/address/list.html\">收货地址</a></p>\r\n");
      out.write("\t\t\t\t\t<p><a href=\"/u/user/updatepwd.html\">密码管理</a></p>\r\n");
      out.write("\t\t\t\t\t<p id=\"merchantLi\" style=\"display: none;\"><a href=\"http://merchant.ibaixiong.com\" target=\"_blank\">代理商</a></p>\r\n");
      out.write("\t\t\t\t\t<p style=\"border-bottom:none;\"><a href=\"/logout.html\">退出</a></p>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t</ul>\r\n");
      out.write("\t<ul class=\"menus clearfix\">\r\n");
      out.write("\t    <li><a href=\"http://merchant.ibaixiong.com\">代理商</a> </li>\r\n");
      out.write("\t\t<li><a href=\"/u/order/list.html\">我的订单</a></li>\r\n");
      out.write("\t\t<li><a href=\"/u/address/list.html\">收货地址</a> </li>\r\n");
      out.write("\t\t<li><a href=\"/u/user/updatepwd.html\">密码管理</a> </li>\r\n");
      out.write("        <!--  <li><a href=\"/wallpaper/overview.html#top\">发热墙纸</a> </li> -->\r\n");
      out.write("\t\t<!-- <li><a href=\"/bathroom/overview.html#top\">暖魔方·浴室款</a> </li> -->\r\n");
      out.write("        <!-- <li><a href=\"/bedroom/overview.html#top\">暖魔方·壁挂款</a> </li> -->\r\n");
      out.write("\t\t<!-- <li><a href=\"http://bbs.ibaixiong.com\">社区</a></li> -->\r\n");
      out.write("\t\t<li><a href=\"/u/car/list.html\">购物车</a></li>\r\n");
      out.write("\t</ul>\r\n");
      out.write("</div>\r\n");
      out.write("<script>\r\n");
      out.write("$(document).ready(function() {\r\n");
      out.write("$.ajax({  \r\n");
      out.write("\t  type:\"get\",  \r\n");
      out.write("\t  dataType:\"jsonp\",\r\n");
      out.write("\t  jsonp: \"jsonpCallback\",\r\n");
      out.write("\t  url: \"/u/user/info.html\",  \r\n");
      out.write("\t  crossDomain:true,\r\n");
      out.write("\t  success: function(obj){  \r\n");
      out.write("\t\t  if ( obj.result.loginstatus == true ) {\r\n");
      out.write("\t\t\t\t//获取登陆用户成功之后给值\r\n");
      out.write("\t\t\t\t$(\"#infoLi\").show();\r\n");
      out.write("\t\t\t\t$(\"#logoutLi\").show();\r\n");
      out.write("\t\t\t\t$(\"#loginLi\").hide();\r\n");
      out.write("\t\t\t\t$(\"#regLi\").hide();\r\n");
      out.write("\t\t\t\tvar nick=obj.result.name;\r\n");
      out.write("\t\t\t\tif(nick==null)\r\n");
      out.write("\t\t\t\t\tnick =\"白熊\";\r\n");
      out.write("\t\t\t\t$(\"#userName\").text(nick);\r\n");
      out.write("\t\t\t\t//4s店，代理商链接显示\r\n");
      out.write("\t\t\t\tvar ssss = obj.result.ssssTag;\r\n");
      out.write("\t\t\t\tif(ssss == 1){\r\n");
      out.write("\t\t\t\t\t$(\"#ssssLi\").show();\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tvar merchantTag = obj.result.merchantTag;\r\n");
      out.write("\t\t\t\tif(merchantTag == 1){\r\n");
      out.write("\t\t\t\t\t$(\"#merchantLi\").show();\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t          \r\n");
      out.write("\t  },  \r\n");
      out.write("\t  beforeSend:function(){\r\n");
      out.write("\t\t  \r\n");
      out.write("\t  },  \r\n");
      out.write("\t  complete:function(data,status){\r\n");
      out.write("\t\t  //alert( JSON.stringify(data));\r\n");
      out.write("\t  },\r\n");
      out.write("    error: function(jqXHR, textStatus, errorThrown) {\r\n");
      out.write("  \t  \r\n");
      out.write("    }\r\n");
      out.write("\t});\r\n");
      out.write("});\r\n");
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
