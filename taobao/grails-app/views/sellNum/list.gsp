	<head>
		<meta name="layout" content="edit" />
		<title>�ֻ������б�</title>
	</head>

	<body>
		<!-- end #header -->
		<!-- end #header-wrapper -->
		
		<div id="page">
			<div id="page-bgtop">

				<div>
					<ul>
						<li>
							<ul>
								<g:each in="${sellNums}" var="phoneNum">
									<li>
										${phoneNum.display} Ԥ���<font color='red'>${phoneNum.phoneNum.prise}</font> 
									</li>
								</g:each>
							</ul>
						</li>
					</ul>
				</div>
				<!-- end #sidebar -->

				<!-- end #content -->
				<div style="clear: both;">&nbsp;
				</div>
			</div>
		</div>
		<!-- end #page -->

		<!-- end #footer -->
	</div>
	</body>
