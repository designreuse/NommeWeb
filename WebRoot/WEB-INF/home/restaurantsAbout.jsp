<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Nomme</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="${ctx}/index/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${ctx}/index/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="${ctx}/index/css/search_list.css">

<script src="${ctx}/index/js/jquery.min.js"></script>
<script src="${ctx}/index/js/bootstrap.min.js"></script>
<script src="${ctx}/index/js/commjs.js"></script>
</head>

<body>
	<div class="search_top">

		<div class="container">
			<div class="row">
				<div class="col-md-5">
					<h4>
						<a name="logo" href="#">
							<img src="${ctx}/index/images/xc-logo.png">
						</a>
					</h4>
				</div>
				<div class="col-md-7">
					<div class="xc-top-login">
						<li><a href="#myModal" data-toggle="modal"
							data-target="#myModal">Sign in</a></li>
					</div>
					<!-- Modal -->

					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog" style="width:500px;">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 id="myModalLabel" align="center">Sign in</h4>
								</div>
								<div class="modal-body">
									<p>Email</p>
									<div class="controls">
										<input type="email" class="form-control"
											id="exampleInputEmail1" placeholder="Enter email"
											style="height:45px;">
									</div>
									<div align="left" style="margin:10px 0;">
										password <span style="float:right; color:#6D9940;">Forgot
											paddword?</span>
									</div>
									<div class="controls">
										<input type="password" class="form-control"
											id="exampleInputPassword1" placeholder="Password"
											style="height:45px;">
									</div>
								</div>
								<div style=" padding:0 15px 15px;">
									<button class="btn btn-large btn-block btn-primary"
										type="button"
										style="height:45px; background:#88878C; border:#88878C">Sign
										In</button>
									<div align="center" style="margin:10px 0;">
										<strong>OR</strong>
									</div>
									<button class="btn btn-default btn-block" type="button"
										style="height:45px; border:#6C9C46 2px solid; color:#6C9C46;">Cretae
										Account</button>
									<div align="right" style="margin-top:15px;">
										<img src="${ctx}/index/images/deng01.jpg">
									</div>

								</div>
							</div>
						</div>
					</div>




				</div>
			</div>
		</div>

	</div>
	<!-- center kai-->

	<div class="container bgbai">
		<div class="row">
			<div class="col-md-8" style="padding:0;">
				<div style="padding:10px;">
					<h3>Kebab Prince</h3>

					<div class="row">
						<div class="col-md-4">
							<img src="${ctx}/index/images/test01.jpg" class="thumbnail">
						</div>
						<div class="col-md-8">
							<h4>
								347 Dalhousie Steet Ottawa, ON <br>GPS Tracking
							</h4>
							<div class="panel panel-default">
								<div class="panel-body">Day and night RestaurantDay and
									night RestaurantDay and night Restaurant</div>
							</div>
						</div>
					</div>
				</div>
				<!--xialakaishi-->
				<div role="tabpanel">

					<!-- Nav tabs -->
					<ul class="rest-xiala rest-xiala-jg" role="tablist">
						<li role="presentation" class="active"><a href="#home"
							aria-controls="home" role="tab" data-toggle="tab">Home</a></li>
						<li role="presentation"><a href="#profile"
							aria-controls="profile" role="tab" data-toggle="tab">Profile</a></li>
						<li role="presentation"><a href="#messages"
							aria-controls="messages" role="tab" data-toggle="tab">Messages</a></li>
						<li role="presentation"><a href="#settings"
							aria-controls="settings" role="tab" data-toggle="tab">Settings</a></li>
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="home">1</div>
						<div role="tabpanel" class="tab-pane" id="profile">2</div>
						<div role="tabpanel" class="tab-pane" id="messages">3</div>
						<div role="tabpanel" class="tab-pane" id="settings">4</div>
					</div>

				</div>
				<!--xialajieshu-->
				<div class="rest-lef-zykuan">
					<h2>Notice</h2>
				</div>
				<div class="zhucyemian-miaodi"></div>
				<div class="rest-lef-zykuan">
					<ul class="rest-about">
						<li>welcome to the ranch rESTAURANT & Saloon!welcome to the
							ranch rESTAURANT & Saloon!welcome to the ranch rESTAURANT &
							Saloon!welcome to the ranch rESTAURANT & Saloon!welcome to the
							ranch rESTAURANT & Saloon!welcome to the ranch rESTAURANT &
							Saloon!welcome to the ranch rESTAURANT & Saloon!welcome to the
							ranch rESTAURANT & Saloon!welcome to the ranch rESTAURANT &
							Saloon!welcome to the ranch rESTAURANT & Saloon!welcome to the
							ranch rESTAURANT & Saloon!welcome to the ranch rESTAURANT &
							Saloon!welcome to the ranch rESTAURANT & Saloon!</li>
					</ul>

				</div>

			</div>
			<div class="col-md-4 rest-leftdi" style="padding:0px;">

				<h3 align="center" class="rest-miaodi">Order Infomation</h3>
				<div role="tabpanel">

					<!-- Nav tabs -->
					<div role="tablist">
						<ul class="rest-left-xiala rest-left-xiala-jg" role="tablist">
							<Li role="presentation" class="active"><a href="#dizhi"
								aria-controls="dizhi" role="tab" data-toggle="tab">Deliery</a></Li>
							<Li role="presentation"><a href="#biaodan"
								aria-controls="biaodan" role="tab" data-toggle="tab">Pick Up</a>
							</Li>
							<Li role="presentation"><a href="#tianxie"
								aria-controls="tianxie" role="tab" data-toggle="tab">Resevation</a>
							</Li>
						</ul>
					</div>

					<!-- Tab panes -->
					<div class="tab-content rest-lef-hua">
						<div role="tabpanel" class="tab-pane active" id="dizhi">
							<div class="row rest-lef-shkuan">
								<div class="col-md-6">
									<h4>
										<img src="${ctx}/index/images/restaurst05.jpg"> Addres
									</h4>
								</div>
								<div class="col-md-6" align="right">
									<img src="${ctx}/index/images/restaurst08.jpg">
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-body">Day and night RestaurantDay and
									night RestaurantDay and night Restaurant</div>
							</div>

						</div>
						<div role="tabpanel" class="tab-pane" id="biaodan">2</div>
						<div role="tabpanel" class="tab-pane" id="tianxie">3</div>
					</div>

				</div>
				<div class="rest-lef-zykuan">
					<h4>
						<img src="${ctx}/index/images/restaurst06.jpg"> When
					</h4>
					<div class="row">
						<div class="col-md-4">

							<select class="form-control-hui " style="color:#FFFFFF;">
								<option>2015-04-06</option>
								<option>2015-04-07</option>
								<option>2015-04-08</option>
								<option>2015-04-09</option>
								<option>2015-04-10</option>
							</select>
						</div>
						<div class="col-md-4">

							<select class="form-control-hui " style="color:#FFFFFF;">
								<option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								<option>5</option>
								<option>6</option>
								<option>7</option>
								<option>8</option>
								<option>9</option>
								<option>10</option>
								<option>11</option>
								<option>12</option>
								<option>13</option>
								<option>14</option>
								<option>15</option>
								<option>16</option>
								<option>17</option>
								<option>18</option>
								<option>19</option>
								<option>20</option>
								<option>21</option>
								<option>22</option>
								<option>23</option>
								<option>24</option>
							</select>:
						</div>

						<div class="col-md-4">

							<select class="form-control-hui" style="color:#FFFFFF;">
								<option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								<option>5</option>
								<option>6</option>
								<option>7</option>
								<option>8</option>
								<option>9</option>
								<option>10</option>
								<option>11</option>
								<option>12</option>
								<option>13</option>
								<option>14</option>
								<option>15</option>
								<option>16</option>
								<option>17</option>
								<option>18</option>
								<option>19</option>
								<option>20</option>
								<option>21</option>
								<option>22</option>
								<option>23</option>
								<option>24</option>
							</select>
						</div>
					</div>
					<div></div>
				</div>

				<div class="rest-lef-zykuan">
					<h4>
						<img src="${ctx}/index/images/restaurst07.jpg"> Order
					</h4>
					<div class="zhucyemian-miaodi"></div>
					<div class="row">
						<div class="col-md-3">
							<img src="${ctx}/index/images/restaurst09.jpg">
						</div>
						<div class="col-md-6 youmian-miaobian">
							<div align="center">
								<strong>COffee</strong>
							</div>
							<div class="zhucyemian-miaodi"></div>
							<p>
								<img src="${ctx}/index/images/restaurst10.jpg">
								<img src="${ctx}/index/images/restaurst11.jpg">
							</p>
						</div>
						<div class="col-md-3">$5.00</div>

					</div>
					<div class="zhucyemian-miaodi"></div>
					<div class="row">
						<div class="col-md-3">
							<img src="${ctx}/index/images/restaurst09.jpg">
						</div>
						<div class="col-md-6 youmian-miaobian">
							<div align="center">
								<strong>COffee</strong>
							</div>
							<div class="zhucyemian-miaodi"></div>
							<p>
								<img src="${ctx}/index/images/restaurst10.jpg">
								<img src="${ctx}/index/images/restaurst11.jpg">
							</p>
						</div>
						<div class="col-md-3">$5.00</div>

					</div>
					<div class="zhucyemian-miaodi"></div>

					<div class="row">
						<div class="col-md-3">
							<img src="${ctx}/index/images/restaurst09.jpg">
						</div>
						<div class="col-md-6 youmian-miaobian">
							<div align="center">
								<strong>COffee</strong>
							</div>
							<div class="zhucyemian-miaodi"></div>
							<p>
								<img src="${ctx}/index/images/restaurst10.jpg"> 
								<img src="${ctx}/index/images/restaurst11.jpg">
							</p>
						</div>
						<div class="col-md-3 ">$5.00</div>

					</div>
					<div class="zhucyemian-miaodi"></div>
					<div class="row">
						<div class="col-md-3">
							<img src="${ctx}/index/images/restaurst09.jpg">
						</div>
						<div class="col-md-6  youmian-miaobian">
							<div align="center">
								<strong>COffee</strong>
							</div>
							<div class="zhucyemian-miaodi"></div>
							<p>
								<img src="${ctx}/index/images/restaurst10.jpg">
								<img src="${ctx}/index/images/restaurst11.jpg">
							</p>
						</div>
						<div class="col-md-3">$5.00</div>

					</div>
					<div class="zhucyemian-miaodi"></div>
				</div>

				<h4 class="rest-h4">
					<img src="${ctx}/index/images/restaurst12.jpg"><a href="#myModa2"
						style="color:#FFFFFF;" data-toggle="modal" data-target="#myModa2">Your
						Order Eligible For Coupons</a>
				</h4>

				<!--dier-->
				<div class="modal fade" id="myModa2" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabe2" aria-hidden="true">
					<div class="modal-dialog" style="width:500px;">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 id="myModalLabel" align="center">Your order is now
									eligibe for the following coupons</h4>
							</div>
							<div class="modal-body">

								<div class="radio">
									<label> <input type="radio" name="optionsRadios"
										id="optionsRadios1" value="option1" checked> Free
										order of pakoras withpurchase of $25 or more
									</label>
								</div>
								<div class="radio">
									<label> <input type="radio" name="optionsRadios"
										id="optionsRadios2" value="option2"> 10% with purchase
										of $25 or more
									</label>
								</div>

								<div class="row">
									<div class="col-md-1"></div>
									<div class="col-md-4">
										<button class="btn btn-default  btn-block" type="button">APPLY</button>
									</div>
									<div class="col-md-1"></div>
									<div class="col-md-4">
										<button class="btn btn-default  btn-block" type="button">Cancel</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!---->

				<div class="rest-lef-zykuan">
					<div class="row">
						<div class="col-md-5">
							<h5>Subtotal:</h5>
						</div>
						<div class="col-md-7" align="right">$5.00</div>
					</div>
					<div class="row">
						<div class="col-md-5">
							<h5>Delivery Fee:</h5>
						</div>
						<div class="col-md-7" align="right">$5.00</div>
					</div>
					<div class="row">
						<div class="col-md-5">
							<h5>Sales Tax:</h5>
						</div>
						<div class="col-md-7" align="right">$5.00</div>
					</div>

					<div class="row">
						<div class="col-md-4">
							<strong>TOTAL:</strong>
						</div>
						<div class="col-md-8" align="right">
							<span style=" color:#67A224; font-size:16px; font-weight:900;">$100.00</span>
						</div>
					</div>

				</div>



			</div>
		</div>
	</div>

	<!-- center jieshu-->

	<!-- dibu kaishi-->
	<div class="search-bottom" align="center">
		<a href="#"><span>Contact Us</span></a>　　About　　FAQ　　Terms of Use　　Privacy polic
		polic
	</div>
</body>
</html>
