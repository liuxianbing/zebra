<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<header class="main-header">
    <!-- Logo -->
    <a href="#" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini">SimClouds</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>Admin</b>SimClouds</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
       <div class="navbar-custom-menu">
       		  <ul class="nav navbar-nav">
       		  		  <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="${ctx }/assets/img/user2-160x160.jpg" class="user-image" alt="User Image">
              <span class="hidden-xs">${CURRENT_USER.phone }</span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="${ctx }/assets/img/user2-160x160.jpg" class="img-circle" alt="User Image">

                <p>
                  Alexander Pierce - Web Developer
                  <small>Member since Nov. 2012</small>
                </p>
              </li>
              <!-- Menu Body 
              <li class="user-body">
                <div class="row">
                  <div class="col-xs-4 text-center">
                    <a href="#">管理员</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Sales</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Friends</a>
                  </div>
                </div>
              </li>
              -->
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="${ctx }/user/account" class="btn btn-default btn-flat">修改账号</a>
                </div>
                <div class="pull-right">
                  <a href="${ctx }/logout" class="btn btn-default btn-flat">退出</a>
                </div>
              </li>
            </ul>
          </li>
       		  </ul>
       </div>

    
    </nav>
  </header>
  
	
	 <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="${ctx }/assets/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>${CURRENT_USER.phone }</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" data-widget="tree">
        <!-- 
        <li class="active treeview">
          <a href="#">
            <i class="fa fa-dashboard"></i> <span>Dashboard</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li class="active"><a href="index.html"><i class="fa fa-circle-o"></i> Dashboard v1</a></li>
            <li><a href="index2.html"><i class="fa fa-circle-o"></i> Dashboard v2</a></li>
          </ul>
        </li>
         -->
         <li>
          <a href="${ctx }/index">
            <i class="fa fa-home"></i> <span>首页</span>           
          </a>
        </li>
        <c:if test="${CURRENT_USER.auth==1 }">
          <li class="treeview">
          <a href="#">
            <i class="fa fa-cc-mastercard"></i>
            <span>卡片管理</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="${ctx }/simcard/list"><i class="fa fa-list-alt"></i>卡片列表</a></li>
            <c:if test="${CURRENT_USER.role>=1 }">
            <li><a href="${ctx }/flow/self"><i class="fa fa-hourglass-2"></i>我的流量池</a></li>
            </c:if>
          </ul>
        </li>
        </c:if>
      
       <c:if test="${CURRENT_USER.role==0 }">
        <li class="treeview">
          <a href="#">
            <i class="fa fa-puzzle-piece"></i>
            <span>分销管理</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="${ctx }/tariffplan/list"><i class="fa fa-paper-plane"></i>资费计划管理</a></li>
            <li><a href="${ctx }/pack/list"><i class="fa fa-paper-plane"></i>系统套餐管理</a></li>
             <li><a href="${ctx }/flow/list"><i class="fa fa-tasks"></i>客户流量池</a></li>
              <li><a href="${ctx }/user/list"><i class="fa fa-users"></i>客户管理</a></li>
          </ul>
        </li>
        </c:if>
          <li class="treeview">
          <a href="#">
            <i class="fa fa-gears"></i>
            <span>设置</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="${ctx }/user/account"><i class="fa fa-user"></i>账户设置</a></li>
              <c:if test="${CURRENT_USER.role==1 }">
              <li><a href="${ctx }/user/list"><i class="fa fa-users"></i>用户列表</a></li>
              </c:if>
          </ul>
        </li>
        <c:if test="${CURRENT_USER.role<=1 }">
          <li class="treeview">
          <a href="#">
            <i class="fa fa-puzzle-piece"></i>
            <span>在线购卡</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
          <c:if test="${CURRENT_USER.role==0 }">
            <li><a href="${ctx }/cart/buy"><i class="fa fa-paper-plane"></i>购买卡</a></li>
            </c:if>
            <li><a href="${ctx }/cart/record"><i class="fa fa-paper-plane"></i>购卡记录</a></li>
          </ul>
        </li>
        </c:if>
         <li class="treeview">
          <a href="#">
            <i class="fa fa-puzzle-piece"></i>
            <span>财务</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="${ctx }/finance/list"><i class="fa fa-paper-plane"></i>账户余额</a></li>
            <c:if test="${CURRENT_USER.role==0 }">
            <li><a href="${ctx }/finance/add"><i class="fa fa-paper-plane"></i>账户充值</a></li>
            </c:if>
          </ul>
        </li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>