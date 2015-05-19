<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common_jsp.jsp"%>

<div class="header">
    <div class="header_resize">
    <!-- 
    <div class="menu_nav">
       <ul>
        <li class="active"><a href="#"><span>Home</span></a></li>
          <li><a href="support.html"><span>Support</span></a></li>
          <li><a href="about.html"><span>About Us</span></a></li>
          <li><a href="blog.html"><span>Blog</span></a></li>
          <li><a href="contact.html"><span>Contact Us</span></a></li> 
      </ul>        
        
      </div>
      
      <div class="clr"></div>
      -->
      <div class="logo">
        <h1><a href="#">webco<span>rp</span></a><small></small></h1>
      </div>
 <div class="search">
        <form id="form" name="form" method="post" action="">
          <span>
          <input name="q" type="text" class="keywords" id="keywords" maxlength="50" value="Search..." />
          
          <input name="b" type="image" src="${ctx}/static/images/search.gif" onclick="search();return false;" class="button" />
          </span>
        </form>
      </div>
      <!--/search -->
      
      <!-- <div class="clr"></div> -->
      <%-- <div class="header_img"><img src="${ctx}/static/images/main_img.jpg" alt="image" width="970" height="223" />
           <div class="clr"></div>
      </div> --%>
      
      
    </div>
  </div>
