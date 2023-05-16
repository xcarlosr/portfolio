<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Home - Gestão de Portfolio</title>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.18/dist/sweetalert2.css">
</head>
<body>
    <div class="container-fluid">
        <div class="row justify-content-center align-items-center">
            <div class="col-sm-12 col-md-10">
                <div id="header-page" class="header">
                    <nav class="navbar navbar-expand-lg navbar-light bg-light">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item">
								<a class="nav-link" href="/">Home</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="/jsp/projetos">Projetos</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="/jsp/pessoas">Pessoas</a>
							</li>
							<li class="nav-item active">
								<a class="nav-link" href="/jsp/projetos/1/membros">Membros</a>
							</li>
                        </ul>
                    </nav>
                </div>
                <div class="table-responsive">
                    <table class="table">
                        <hr>
                        <h4>Projeto</h4>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Status</th>
                                <th>Risco</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${membros.projeto.id}</td>
                                <td>${membros.projeto.nome}</td>
                                <td>${membros.projeto.status}</td>
                                <td>${membros.projeto.risco}</td>
                            </tr>
                        </tbody>
                    </table>
                    <hr>
                    <h4>Membros</h4>
                    <table id="tabela-membros" class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Funcionário</th>
                            </tr>
                        </thead>
                        <tbody id="tabela-membros">
                            <c:forEach var="pessoa" items="${membros.pessoas}">
                                <tr>
                                    <td>${pessoa.id}</td>
                                    <td>${pessoa.nome}</td>
                                    <td>${pessoa.funcionario ? 'Sim' : 'Não'}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <div class="footer">
                    
                </div>
            </div>
        </div>
    </div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-maskmoney/3.0.2/jquery.maskMoney.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.18/dist/sweetalert2.js"></script>
</body>
</html>
