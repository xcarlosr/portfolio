<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Home - Gestão de Portfolio</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <div class="container-fluid">
		<div class="row justify-content-center align-items-center">
            <div class="col-sm-12 col-md-10">
				<div id="header-page" class="header">
					<c:import url="templates/navbar.jsp">
						<c:param name="activeNavItem" value="home" />
					</c:import>
				</div>
				<div class="content d-flex justify-content-center align-items-center">
					<div class="container-fluid col-md-10" >
						<h1 class="display-4"><b>Gestão de Portfólio</b></h1>
						<p class="lead">Auxilia na classificação dos projetos de acordo com o planejamento estabelecido, na atribuição de novos recursos, no acompanhamento do progresso, permitindo uma visão clara e organizada de todo o portfólio.</p>
					</div>
				
			</div>
		</div>	
	</div>
</body>
</html>
