package org.apache.jsp.WEB_002dINF.view.jsp.ui.product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class detail_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

static private org.apache.jasper.runtime.ProtectedFunctionMapper _jspx_fnmap_0;

static {
  _jspx_fnmap_0= org.apache.jasper.runtime.ProtectedFunctionMapper.getMapForFunction("fn:indexOf", org.apache.taglibs.standard.functions.Functions.class, "indexOf", new Class[] {java.lang.String.class, java.lang.String.class});
}

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fminFractionDigits_005fmaxFractionDigits_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fminFractionDigits_005fmaxFractionDigits_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fminFractionDigits_005fmaxFractionDigits_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
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

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("\t<head>\n");
      out.write("\t\t<meta charset=\"utf-8\">\n");
      out.write("\t\t<title>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${product.title}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("_熊爸爸</title>\n");
      out.write("\t\t<link href=\"http://fe.ibaixiong.com/common/plug/bootstrap/bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\">\n");
      out.write("\t\t<link href=\"http://fe.ibaixiong.com/common/plug/cropper.css\" rel=\"stylesheet\" type=\"text/css\">\n");
      out.write("\t\t<link href=\"http://fe.ibaixiong.com/shop/css/common.css\" rel=\"stylesheet\" type=\"text/css\">\n");
      out.write("\t\t<link href=\"http://fe.ibaixiong.com/shop/css/pay.css\" rel=\"stylesheet\" type=\"text/css\">\n");
      out.write("\t\t<link href=\"/css/specification.css\" rel=\"stylesheet\" type=\"text/css\">\n");
      out.write("\t\t<script src=\"http://fe.ibaixiong.com/common/plug/jQuery/jquery-1.9.1.min.js\" type=\"text/javascript\" ></script>\n");
      out.write("\t\t<script src=\"http://fe.ibaixiong.com/common/plug/jQuery/jquery.rotate.min.js\" type=\"text/javascript\" ></script>\n");
      out.write("\t\t<script src=\"http://fe.ibaixiong.com/common/plug/bootstrap/bootstrap.min.js\" type=\"text/javascript\" ></script>\n");
      out.write("\t\t<script src=\"/plug/cropper.js\" type=\"text/javascript\" ></script>\n");
      out.write("\t\t<script src=\"/plug/main.js\" type=\"text/javascript\" ></script>\n");
      out.write("    \t<script src=\"/js/data.js\"></script>\n");
      out.write("\t</head>\n");
      out.write("\t<body>\n");
      out.write("\t\t<div class=\"container clearfix\">\n");
      out.write("\t\t<div class=\"content\">\n");
      out.write("\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../include/top.jsp", out, false);
      out.write("\n");
      out.write("\t\t<form action=\"/u/order/buy.html\" method=\"post\" id=\"formBuy\" onsubmit=\"return check()\">\n");
      out.write("\t\t<div class=\"article\">\n");
      out.write("\t\t\t<div class=\"product\">\n");
      out.write("\t\t\t\t<span class=\"name\" data-num=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bottomPriceFormat.stock }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write('"');
      out.write('>');
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${product.title}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</span>\n");
      out.write("\t\t\t\t<span class=\"price\"  data-value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bottomPriceFormat.price}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\">\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t</span>\n");
      out.write("\t\t\t\t<input type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ bottomPriceFormat.propertyValueCombine}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" id=\"flag\" />\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"picId\" id=\"picId\" />\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"mallCustomPicId\" id=\"mallCustomPicId\" />\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"left\">\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"right \" unselectable=\"on\" style=\"-moz-user-select:none;\" onselectstart=\"return false;\">\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_c_005fforEach_005f1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t<!-- B端不可见的商品规格 -->\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_c_005fforEach_005f4(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t<div id=\"delimiter\"></div>\n");
      out.write("\t\t\t\t<!-- 墙纸面积 -->\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t<!-- 数量 -->\n");
      out.write("\t\t\t\t<p class=\"fomcat-name\" id=\"numExplain\">\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f8(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f9(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t</p>\n");
      out.write("\t\t\t\t<div class=\"change\">\n");
      out.write("\t\t\t\t\t<span class=\"minus\" data-btn=\"\"></span>\n");
      out.write("\t\t\t\t\t<input type=\"text\" name=\"num\" data-txt=\"num\" data-type=\"1\" class=\"number\" value=\"1\" onchange=\"keyup(this.value,$(this))\"/>\n");
      out.write("\t\t\t\t\t<span class=\"unit\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bottomPriceFormat.unit }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</span>\n");
      out.write("\t\t\t\t\t<span class=\"plus\" data-btn=\"\"></span>\n");
      out.write("\t\t\t\t<p class=\"fomcat-remark\" style=\"display:none;width:100px;float:left;line-height:40px;\">(库存不足)</p>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<input type=\"hidden\" id=\"productId\" name=\"productId\"  value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${product.id }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\n");
      out.write("\t\t<input type=\"hidden\" name=\"format\" id=\"format\" value=\"\" />\n");
      out.write("\t\t<input type=\"hidden\" name=\"isCustomMade\" value=\"0\" id=\"isCustomMade\">\n");
      out.write("\t\t<input type=\"hidden\" name=\"tag\" id=\"area\" value=\"1\">\n");
      out.write("\t\t<input type=\"hidden\" name=\"isExt\" id=\"thisExt\" value=\"0\">\n");
      out.write("\t\t<input type=\"hidden\" name=\"formatId\" id=\"formatId\" value=\"0\">\n");
      out.write("\t\t<input type=\"hidden\" name=\"Save_arae\" id=\"Save_arae\" value=\"1\"><!-- 墙纸面积-->\n");
      out.write("\t    <input type=\"hidden\" name=\"Ground\" id=\"Ground_arae\" value=\"1\"><!-- 地面面积-->\n");
      out.write("\t\t</form>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div class=\"buy\">\n");
      out.write("\t\t\t<div class=\"buybox\">\n");
      out.write("\t\t\t<div class=\"buy-infor\">\n");
      out.write("\t\t\t\t<p class=\"productname\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${product.title}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</p>\n");
      out.write("\t\t\t\t<p class=\"productinfor\"><span class=\"inforText\">飘窗款  1200W  土豪金  私人订制</span><span>x<span class=\"numberText\">1</span></span></p>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"addbuy\" style=\"margin-top: 10px;\">\n");
      out.write("\t\t\t\t<div class=\"add_Agreement\">\n");
      out.write("\t\t\t\t  <span class=\"buyprice\"></span>\n");
      out.write("\t\t\t\t  <a href=\"javascript:void(0)\" class=\"buycar\">加入购物车</a>\n");
      out.write("\t\t\t\t  <p style=\"display:none\">点击立即购买表示您知晓并同时意<a href=\"#\">熊爸爸发热墙纸下单协议</a></p>\n");
      out.write("\t\t\t\t</div>  \t\n");
      out.write("\t\t\t\t<a href=\"javascript:void(0)\" class=\"submitLink\">立即购买</a>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div class=\"uploadpop\" style=\"display: none;z-index:995\">\n");
      out.write("\t\t\t<div class=\"layel\">\n");
      out.write("\t\t\t\t<h3 class=\"poptitle\"><span class=\"greyword\">选择产品规格></span>私人定制<i class=\"closeicon uploadclose\"></i> </h3>\n");
      out.write("\t\t\t\t<div class=\"leftLayel\">\n");
      out.write("\t\t\t\t\t<div class=\"prodBox\"></div>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t\t<div class=\"rightLayel\">\n");
      out.write("\t\t\t\t\t<p class=\"text\" style=\"margin-top:100px;\">您可以选择一张本地照片做产品正面图案印刷</p>\n");
      out.write("\t\t\t\t\t<p class=\"text\">为了保证印刷精细，请上传高度大于<span class=\"numberword\">4000</span>像素，宽度大于<span class=\"numberword\">2000</span>像素的照片</p>\n");
      out.write("\t\t\t\t\t<p class=\"text blueword\"><a href=\"http://bbs.ibaixiong.com/detail/368.html\" target=\"_blank\">如何查看像素点?</a></p>\n");
      out.write("\t\t\t\t\t<div class=\"row tc uploadbox\">\n");
      out.write("\t\t\t\t\t\t<input type=\"button\" value=\"上传本地图片\" class=\"upload\">\n");
      out.write("\t\t\t\t\t\t<input type=\"file\" value=\"上传本地图片\" class=\"upload file\">\n");
      out.write("\t\t\t\t\t\t<span class=\"uploading-animate\"></span>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<p class=\"text\">私人定制图片<span class=\"blueword\">请勿参照</span>上图,下单后,客服会第一时间与您联系,我们将为您打造私人完美!</p>\n");
      out.write("\t\t\t\t\t<!-- <div class=\"row tc\" style=\"position:relative;margin-top:100px;\">\n");
      out.write("\t\t\t\t\t\t<input type=\"button\" value=\"确认\" class=\"sureUpload\">\n");
      out.write("\t\t\t\t\t\t<div class=\"\"></div>\t\t\t\t\t<input type=\"button\" value=\"重新上传图片\" class=\"upload reUpload\">\n");
      out.write("\t\t\t\t\t\t<input type=\"file\" value=\"重新上传图片\" class=\"upload file reUpload\">\n");
      out.write("\t\t\t\t\t</div> -->\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write(" \t\t<div class=\"successpop\" style=\"display: none;\">\n");
      out.write(" \t\t<div class=\"layel\">\n");
      out.write("\t\t  <div class=\"container\" id=\"crop-avatar\" style=\"background:none;\">\n");
      out.write("\t\t    <!-- Current avatar -->\n");
      out.write("\t\t    <div class=\"avatar-view\" title=\"Change the avatar\" style=\"opacity:0;\">\n");
      out.write("\t\t      <img src=\"#\" alt=\"Avatar\">\n");
      out.write("\t\t    </div>\n");
      out.write("\t\t    <!-- Cropping modal -->\n");
      out.write("\t\t    <div class=\"modal fade\" id=\"avatar-modal\" aria-hidden=\"true\" aria-labelledby=\"avatar-modal-label\" role=\"dialog\" tabindex=\"-1\" style=\"width:1200px;margin:auto;\">\n");
      out.write("\t\t      <div class=\"modal-dialog modal-lg\" style=\"width:1200px;\">\n");
      out.write("\t\t        <div class=\"modal-content\">\n");
      out.write("\t\t          <form class=\"avatar-form\" enctype=\"multipart/form-data\" method=\"post\">\n");
      out.write("\t\t            <div class=\"modal-header\">\n");
      out.write("\t\t              <button class=\"close closeicon  uploadclose\" type=\"button\"></button>\n");
      out.write("\t\t              <h4 class=\"modal-title\" id=\"avatar-modal-label\">私人定制</h4>\n");
      out.write("\t\t            </div>\n");
      out.write("\t\t            <div class=\"modal-body\">\n");
      out.write("\t\t              <div class=\"avatar-body\">\n");
      out.write("\t\t                <!-- Crop and preview -->\n");
      out.write("\t\t                <div class=\"row\">\n");
      out.write("\t\t                  <div class=\"col-md-9\">\n");
      out.write("\t\t                    <div class=\"avatar-wrapper\"></div>\n");
      out.write("\t\t                    <div class=\"promptword\">图片选取确定后客服会与您取得联系并对图片做相应修改与美化，<span style=\"color:#ff6200;\">实际定制图以双方确认后为准！</span></div>\n");
      out.write("\t\t                    <div class=\"btn-list\" style=\"text-align:center;margin-top:20px;position:relative;\">\n");
      out.write("\t\t                    \t<div class=\"uploading-animate2\"></div>\n");
      out.write("\t\t\t                    <div class=\"btn-group\">\n");
      out.write("\t\t\t                      <input type=\"button\" class=\"btn btn-left avatar-btns\" data-method=\"rotate\" data-option=\"-90\" type=\"button\" title=\"Rotate -90 degrees\">\n");
      out.write("\t\t\t                    </div>\n");
      out.write("\t\t\t                    <div class=\"btn-group\">\n");
      out.write("\t\t\t                      <input type=\"button\" class=\"btn btn-right avatar-btns\" data-method=\"rotate\" data-option=\"90\" type=\"button\" title=\"Rotate -90 degrees\">\n");
      out.write("\t\t\t                    </div><br>\n");
      out.write("\t\t\t                    <div class=\"btn-group\" style=\"margin-top: 10px;\">\n");
      out.write("\t\t\t                    \t<input type=\"button\" value=\"确定\" class=\"upload-sure\">\n");
      out.write("\t\t\t                    </div>\n");
      out.write("\t\t\t                    <div class=\"btn-group\" style=\"margin-top: 10px;\">\n");
      out.write("\t\t\t                    \t<input type=\"button\" value=\"重新上传\" class=\"re-upload\">\n");
      out.write("\t\t\t                    </div>\n");
      out.write("\t\t                    </div>\n");
      out.write("\t\t                  </div>\n");
      out.write("\t\t                  <div class=\"col-md-3\" style=\"position:relative;\">\n");
      out.write("\t\t                    \t<img src=\"http://fe.ibaixiong.com/shop/images/previewBg.png\" class=\"previewBg\">\n");
      out.write("\t\t                    <div class=\"avatar-preview preview-lg\">\n");
      out.write("\t\t                    </div>\n");
      out.write("\t\t                  </div>\n");
      out.write("\t\t                </div>\n");
      out.write("\t\t              </div>\n");
      out.write("\t\t            </div>\n");
      out.write("\t\t          </form>\n");
      out.write("\t\t        </div>\n");
      out.write("\t\t      </div>\n");
      out.write("\t\t    </div><!-- /.modal -->\n");
      out.write("\t\t\n");
      out.write("\t\t    <!-- Loading state -->\n");
      out.write("\t\t    <!-- <div class=\"loading\" aria-label=\"Loading\" role=\"img\" tabindex=\"-1\"></div> -->\n");
      out.write("\t\t  </div>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div class=\"upfailedpop\" style=\"display: none;\">\n");
      out.write("\t\t\t<div class=\"popbg\"></div>\n");
      out.write("\t\t\t<div class=\"layel2\">\n");
      out.write("\t\t\t\t<h3 class=\"poptitle\" style=\"margin-bottom:30px;\">上传出错 </h3>\n");
      out.write("\t\t\t\t<p class=\"text\">对不起，上传的图片尺寸不满足要求，为保证印刷的质量，请重新上传高度大于500像素，宽度大于300像素的照片。</p>\n");
      out.write("\t\t\t\t<p class=\"text blueword\"><a href=\"#\">如何查看像素点?</a></p>\n");
      out.write("\t\t\t\t<div class=\"row tc\">\n");
      out.write("\t\t\t\t\t<input type=\"button\" value=\"重新上传\" class=\"re-upload\">\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<!-- 发热壁纸弹窗 -->\n");
      out.write("\t\t<div class=\"paperpop\">\n");
      out.write("\t\t\t<div class=\"popbg\"></div>\n");
      out.write("\t\t    <div class=\"layel\">\n");
      out.write("\t\t\t\t <div class=\"red d_wrap\">\n");
      out.write("\t\t\t\t  <div class=\"d_left\"><span>注意事项:</span></div>\n");
      out.write("\t\t\t\t  <div class=\"d_right\">\n");
      out.write("\t\t\t\t    <p>1.发热墙纸需整面墙张贴；</p>\n");
      out.write("\t\t\t\t    <p>2.发热墙纸前<b>不能被衣柜等大型家具遮挡，</b>否则将严重影响取暖效果；</p>\n");
      out.write("\t\t\t\t  </div>\n");
      out.write("\t\t\t\t </div>\n");
      out.write("\t\t\t\t <div class=\"d_mian\">\n");
      out.write("\t\t\t\t   <div class=\"d_list d_list1\">\n");
      out.write("\t\t\t\t      <div class=\"pic-house\">\n");
      out.write("\t\t\t\t         <div class=\"wallbox\" id=\"wallbox\">\n");
      out.write("\t\t\t\t\t\t\t<div class=\"wall wallfront\" ><span class=\"wallnumber wallnumber1\"></span></div>\n");
      out.write("\t\t\t\t\t\t    <div class=\"wall wallback\"  ><span class=\"wallnumber wallnumber2\">共需发热墙纸<b id=\"total_money\">0</b>m²</span><span class=\"wallbox_width\">20m</span><span class=\"wallbox_height\">20m</span></div>\n");
      out.write("\t\t\t\t\t\t    <div class=\"wall wallright\" ><span class=\"wallnumber wallnumber3\"></span><span class=\"wallbox_long\">20m</span></div>\n");
      out.write("\t\t\t\t\t\t    <div class=\"wall wallleft\"  ><span class=\"wallnumber wallnumber4\"></span></div>\n");
      out.write("\t\t\t\t\t\t    <div class=\"wall walltop\"></div>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t       </div>\n");
      out.write("\t\t\t\t   </div>\n");
      out.write("\t\t\t\t   <div class=\"d_list d_list2\">\n");
      out.write("\t\t\t\t    <div class=\"row list_top\">\n");
      out.write("\t\t\t\t      <div class=\"d_box\" >\n");
      out.write("\t\t\t\t         <a href=\"###\"   data-i=\"1\">\n");
      out.write("\t\t\t\t\t         <span class=\"icon iconfont\">&#xe66c;</span>\n");
      out.write("\t\t\t\t\t         <i class=\"icon iconfont icon-duihao d_size\"></i>\n");
      out.write("\t\t\t\t\t         <p>客厅</p>\n");
      out.write("\t\t\t\t         </a>\n");
      out.write("\t\t\t\t      </div>\n");
      out.write("\t\t\t\t      <div class=\"d_box\">\n");
      out.write("\t\t\t\t          <a href=\"###\"   data-i=\"2\" >\n");
      out.write("\t\t\t\t\t          <span class=\"icon iconfont\">&#xe623;</span>\n");
      out.write("\t\t\t\t\t          <i class=\"icon iconfont icon-yuanshixin d_size\"></i>\n");
      out.write("\t\t\t\t\t          <p>主卧</p>\n");
      out.write("\t\t\t\t          </a>\n");
      out.write("\t\t\t\t      </div>\n");
      out.write("\t\t\t\t      <div class=\"d_box\" >\n");
      out.write("\t\t\t\t         <a href=\"###\" data-i=\"3\">\n");
      out.write("\t\t\t\t\t         <span class=\"icon iconfont\">&#xe615;</span>\n");
      out.write("\t\t\t\t\t         <i class=\"icon iconfont icon-yuanshixin d_size\"></i>\n");
      out.write("\t\t\t\t\t         <p>次卧</p>\n");
      out.write("                         </a>\t\t\t\t         \n");
      out.write("\t\t\t\t      </div>\n");
      out.write("\t\t\t\t      <div class=\"d_box d_height\">\n");
      out.write("\t\t\t\t         <a href=\"###\"  data-i=\"4\">\n");
      out.write("\t\t\t\t\t         <span class=\"icon iconfont\">&#xe617;</span>\n");
      out.write("\t\t\t\t\t         <i class=\"icon iconfont icon-yuanshixin d_size\"></i>\n");
      out.write("\t\t\t\t\t         <p>书房</p>\n");
      out.write("\t\t\t\t         </a>\n");
      out.write("\t\t\t\t      </div>\n");
      out.write("\t\t\t\t      <div class=\"d_box d_height\">\n");
      out.write("\t\t\t\t          <a href=\"###\"  data-i=\"5\">\n");
      out.write("\t\t\t\t\t          <span class=\"icon iconfont\">&#xe607;</span>\n");
      out.write("\t\t\t\t\t          <i class=\"icon iconfont icon-yuanshixin d_size\"></i>\n");
      out.write("\t\t\t\t\t          <p>走廊</p>\n");
      out.write("\t\t\t\t          </a>\n");
      out.write("\t\t\t\t      </div>\n");
      out.write("\t\t\t\t      <div class=\"d_box d_height\">\n");
      out.write("\t\t\t\t          <a href=\"###\"  data-i=\"6\">\n");
      out.write("\t\t\t\t\t          <span class=\"icon iconfont\">&#xe8d2;</span>\n");
      out.write("\t\t\t\t\t          <i class=\"icon iconfont icon-yuanshixin d_size\"></i>\n");
      out.write("\t\t\t\t\t          <p>餐厅</p>\n");
      out.write("\t\t\t\t          </a>\n");
      out.write("\t\t\t\t      </div>\n");
      out.write("\t\t\t\t      <div class=\"d_box d_height\">\n");
      out.write("\t\t\t\t          <a href=\"###\"  data-i=\"7\">\n");
      out.write("\t\t\t\t\t\t      <span class=\"icon iconfont\">&#xe620;</span>\n");
      out.write("\t\t\t\t\t\t      <i class=\"icon iconfont icon-yuanshixin d_size\"></i>\n");
      out.write("\t\t\t\t\t\t      <p>其他</p>\n");
      out.write("\t\t\t\t          </a>\n");
      out.write("\t\t\t\t      </div>\n");
      out.write("\t\t\t\t    </div>\n");
      out.write("\t\t\t\t   </div>\n");
      out.write("\t\t\t\t </div>\n");
      out.write("\t\t\t\t <input type=\"button\" class=\"paper-close\" id=\"paper-close\" value=\"下一步\">\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<input type=\"hidden\" id=\"picWidth\">\n");
      out.write("\t\t<input type=\"hidden\" id=\"picLength\">\n");
      out.write("\t\t<input type=\"hidden\" id=\"picId_\"/>\n");
      out.write("\t\t<input type=\"hidden\" id=\"x\">\n");
      out.write("\t\t<input type=\"hidden\" id=\"y\">\n");
      out.write("\t\t<input type=\"hidden\" id=\"width\">\n");
      out.write("\t\t<input type=\"hidden\" id=\"height\">\n");
      out.write("\t\t<input type=\"hidden\" id=\"degree\" value=\"0\">\n");
      out.write("\t\t<!-- <div class=\"Protocol\" style=\"display: none;\">//协议\n");
      out.write("\t\t\t <div class=\"Protocol_box\">\n");
      out.write("\t\t\t  <h3>熊爸爸发热墙纸下单协议</h3>\n");
      out.write("\t\t\t  <p>近日有媒体报道称，巴铁项目的融资方华赢凯来资产管理公司已经暂停兑付，部分员工工资也遭到拖欠。公司解释称是公司回款跟支出不成正比。 </p>\n");
      out.write("\t\t\t  <p>华赢凯来与巴铁科技的董事长为同一人，均为白志明。但有内部人士向记者表示，风波过后，白志明开始退居到巴铁公司幕后，专心负责集团层面的经营管理，鲜少过问巴铁公司的事项。 </p>\n");
      out.write("\t\t\t  <p>李楠告诉记者，目前白志明在公司的头衔是“监事”。当有一些重要的接待场合，其他几个副总忙不开了也会由白志明出面。 </p>\n");
      out.write("\t\t\t  <p>宋有洲也向记者表示，白志明不太过问巴铁科技的具体业务，具体业务有几个副总在管。  </p>\n");
      out.write("\t\t\t  <p>澎湃新闻记者观察到，白志明此前有一个名为“巴铁之父-白志明”的认证微博，认证信息为巴铁科技公司董事长。但经过8月以来的风波，这一微博认证已经被悄悄取消了。但工商资料显示，白志明还是巴铁科技的两个自然人股东之一，持股比例也未发生变化。</p>\n");
      out.write("\t\t\t  <div class=\"Protocol_btn\">\n");
      out.write("\t\t\t  \t<a class=\"Protocol_close\" href=\"###\">关闭</a>\n");
      out.write("\t\t\t  </div>\t\n");
      out.write("\t\t\t</div>-->\n");
      out.write("\t\t</div> \n");
      out.write("\t</body>\n");
      out.write("</html>\n");
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

  private boolean _jspx_meth_fmt_005fformatNumber_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:formatNumber
    org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag) _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fminFractionDigits_005fmaxFractionDigits_005fnobody.get(org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag.class);
    _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
    _jspx_th_fmt_005fformatNumber_005f0.setParent(null);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(31,4) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fmt_005fformatNumber_005f0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bottomPriceFormat.price}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    // /WEB-INF/view/jsp/ui/product/detail.jsp(31,4) name = type type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fmt_005fformatNumber_005f0.setType("currency");
    // /WEB-INF/view/jsp/ui/product/detail.jsp(31,4) name = maxFractionDigits type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fmt_005fformatNumber_005f0.setMaxFractionDigits(2);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(31,4) name = minFractionDigits type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fmt_005fformatNumber_005f0.setMinFractionDigits(2);
    int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
    if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fminFractionDigits_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fminFractionDigits_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f0.setParent(null);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(38,4) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setItems(new org.apache.jasper.el.JspValueExpression("/WEB-INF/view/jsp/ui/product/detail.jsp(38,4) '${productPics}'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${productPics}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    // /WEB-INF/view/jsp/ui/product/detail.jsp(38,4) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setVar("pic");
    // /WEB-INF/view/jsp/ui/product/detail.jsp(38,4) name = varStatus type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setVarStatus("status");
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("\t\t\t\t\t");
          if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
            return true;
          out.write("\n");
          out.write("\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f0.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f0);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(39,5) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${status.first}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t\t\t<img src=\"");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pic.url}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\"  class=\"bigphone\"/>\n");
        out.write("\t\t\t\t\t\t<img src=\"\" class=\"customPic\">\n");
        out.write("\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f1 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f1.setParent(null);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(46,4) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f1.setItems(new org.apache.jasper.el.JspValueExpression("/WEB-INF/view/jsp/ui/product/detail.jsp(46,4) '${properties }'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${properties }",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    // /WEB-INF/view/jsp/ui/product/detail.jsp(46,4) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f1.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f1 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
      if (_jspx_eval_c_005fforEach_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("\t\t\t\t\t<p class=\"fomcat-name\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.propertyName }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
            return true;
          out.write("</p>\n");
          out.write("\t\t\t\t\t");
          if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
            return true;
          out.write("\n");
          out.write("\t\t\t\t\t");
          if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
            return true;
          out.write("\n");
          out.write("\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f1[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f1.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f1);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(47,49) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.isCustomMade==1 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
    if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("<span class=\"notes\">选择私人订制可以上传图案</span>");
        int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f2 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f1);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(48,5) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f2.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.displayFormat==1 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
    if (_jspx_eval_c_005fif_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t\t\t<div class=\"row\">\n");
        out.write("\t\t\t\t\t\t\t");
        if (_jspx_meth_c_005fforEach_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
          return true;
        out.write("\n");
        out.write("\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f2 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f2);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(50,7) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f2.setItems(new org.apache.jasper.el.JspValueExpression("/WEB-INF/view/jsp/ui/product/detail.jsp(50,7) '${item.propertiesValues }'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${item.propertiesValues }",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    // /WEB-INF/view/jsp/ui/product/detail.jsp(50,7) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f2.setVar("valueItem");
    int[] _jspx_push_body_count_c_005fforEach_005f2 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
      if (_jspx_eval_c_005fforEach_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("\t\t\t\t\t\t\t\t<div class=\"col-md-6\"><div class=\"box valueItem\" data-id=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${valueItem.id }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${valueItem.value }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</div></div>\n");
          out.write("\t\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f2[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f2.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f3 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f1);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(55,5) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f3.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.displayFormat==2 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
    if (_jspx_eval_c_005fif_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t\t\t<div class=\"row private-order\">\n");
        out.write("\t\t\t\t\t\t\t");
        if (_jspx_meth_c_005fforEach_005f3(_jspx_th_c_005fif_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
          return true;
        out.write("\n");
        out.write("\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f3 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f3);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(57,7) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f3.setItems(new org.apache.jasper.el.JspValueExpression("/WEB-INF/view/jsp/ui/product/detail.jsp(57,7) '${item.propertiesValues }'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${item.propertiesValues }",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    // /WEB-INF/view/jsp/ui/product/detail.jsp(57,7) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f3.setVar("valueItem");
    int[] _jspx_push_body_count_c_005fforEach_005f3 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
      if (_jspx_eval_c_005fforEach_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("\t\t\t\t\t\t\t\t<div class=\"col-md-3 imgbox\">\n");
          out.write("\t\t\t\t\t\t\t\t\t<img src=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${valueItem.imageUrl }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" class=\"pattern valueItem ");
          if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
            return true;
          out.write("\"  data-id=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${valueItem.id }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\"/>\n");
          out.write("\t\t\t\t\t\t\t\t\t<p class=\"bottomtext\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${valueItem.value }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</p>\n");
          out.write("\t\t\t\t\t\t\t\t</div>\n");
          out.write("\t\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f3[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f3.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f4 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f3);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(59,68) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f4.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${fn:indexOf(valueItem.value,'订制')>0 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, _jspx_fnmap_0, false)).booleanValue());
    int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
    if (_jspx_eval_c_005fif_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("designed");
        int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f4 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f4.setParent(null);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(67,4) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f4.setItems(new org.apache.jasper.el.JspValueExpression("/WEB-INF/view/jsp/ui/product/detail.jsp(67,4) '${noneFormats }'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${noneFormats }",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    // /WEB-INF/view/jsp/ui/product/detail.jsp(67,4) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f4.setVar("format");
    // /WEB-INF/view/jsp/ui/product/detail.jsp(67,4) name = varStatus type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f4.setVarStatus("status");
    int[] _jspx_push_body_count_c_005fforEach_005f4 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f4 = _jspx_th_c_005fforEach_005f4.doStartTag();
      if (_jspx_eval_c_005fforEach_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("\t\t\t\t\t<input type=\"hidden\" id=\"none_name");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${status.count }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" name=\"f_name\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${format.name }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\"/>\n");
          out.write("\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f4.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f4[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f4.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f4.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f5 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f5.setParent(null);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(72,4) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f5.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bottomPriceFormat.isExtProperties==1 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
    if (_jspx_eval_c_005fif_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t<div class=\"extPro\">\n");
        out.write("\t\t\t\t\t<p class=\"fomcat-name isext\">房屋尺寸<span>（单位:米）</span></p>\n");
        out.write("\t\t\t\t\t<div class=\"change isext\">\n");
        out.write("\t\t\t\t\t\t");
        if (_jspx_meth_c_005fforEach_005f5(_jspx_th_c_005fif_005f5, _jspx_page_context))
          return true;
        out.write("\n");
        out.write("\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t</div>\n");
        out.write("\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f5 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f5.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f5);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(76,6) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f5.setItems(new org.apache.jasper.el.JspValueExpression("/WEB-INF/view/jsp/ui/product/detail.jsp(76,6) '${exts}'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${exts}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    // /WEB-INF/view/jsp/ui/product/detail.jsp(76,6) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f5.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f5 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f5 = _jspx_th_c_005fforEach_005f5.doStartTag();
      if (_jspx_eval_c_005fforEach_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("\t\t\t\t\t\t\t");
          if (_jspx_meth_c_005fif_005f6(_jspx_th_c_005fforEach_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f5))
            return true;
          out.write("\n");
          out.write("\t\t\t\t\t\t\t");
          if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fforEach_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f5))
            return true;
          out.write("\n");
          out.write("\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f5.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f5[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f5.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f5.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f6(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f5)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f6 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f5);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(77,7) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f6.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.type==1 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
    if (_jspx_eval_c_005fif_005f6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t<span>");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.propertyName }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</span>\n");
        out.write("\t\t\t\t\t\t\t\t<input type=\"text\" name=\"");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.identify }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\" data-txt=\"");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.identify }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\" class=\"");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.identify }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write(" sizes\"  value=\"0\"/>\n");
        out.write("\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f5)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f7 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f5);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(81,7) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f7.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.type==4 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
    if (_jspx_eval_c_005fif_005f7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t<span class=\"icon_house\">面积计算</span>\n");
        out.write("\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f8 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f8.setParent(null);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(90,5) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f8.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ bottomPriceFormat.explain==null}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
    if (_jspx_eval_c_005fif_005f8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t\t数量\n");
        out.write("\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f9(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f9 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f9.setParent(null);
    // /WEB-INF/view/jsp/ui/product/detail.jsp(93,5) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f9.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ bottomPriceFormat.explain!=null}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
    if (_jspx_eval_c_005fif_005f9 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bottomPriceFormat.explain }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("<span>（推荐不低于<i class=\"recom-area\">0</i>");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bottomPriceFormat.unit }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("）</span>\n");
        out.write("\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
    return false;
  }
}
