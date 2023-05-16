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
							<li class="nav-item active">
								<a class="nav-link" href="/jsp/pessoas">Pessoas</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="/jsp/projetos/1/membros">Membros</a>
							</li>
                        </ul>
                    </nav>
                </div>
                <div class="content">
                    <div class="row">
                        <div class="col d-flex align-items-center">
                          <h3>Pessoas</h3>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table id="tabela-pessoas" class="table table-striped">
                            <thead>
                                <tr>
                                    <th style="width: 10px;">ID</th>
                                    <th style="width: 30%;">Nome</th>
                                    <th style="width: 20%;">Nascimento</th>
                                    <th style="width: 20%;">CPF</th>
                                    <th style="width: 25%;">cargo</th>
                                    <th style="width: 10%;">Funcionário</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="pessoa" items="${pessoas.content}">  
                                    <tr>
                                        <td>${pessoa.id}</td>
                                        <td>${pessoa.nome}</td>
                                        <td>${pessoa.dataNascimento}</td>
                                        <td>${pessoa.cpf}</td>
                                        <td>${pessoa.cargo}</td>
                                        <td>${pessoa.funcionario}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div style="padding-right: 13px;" class="row justify-content-end">
                        <ul class="pagination">
                            <li class="page-item ${pessoas.first ? 'disabled' : ''}">
                                <a class="page-link" href="/jsp/pessoas?page=${pessoas.number - 1}" aria-label="Anterior">Anterior<a>
                            </li>
                            <c:choose>
                                <c:when test="${pessoas.totalPages <= 5}">
                                    <c:forEach var="i" begin="0" end="${pessoas.totalPages - 1}">
                                        <c:set var="pageNumber" value="${i}" />
                                        <li class="page-item ${pessoas.number == i ? 'active' : ''}">
                                            <a class="page-link" href="/jsp/pessoas?page=${pageNumber}">${pageNumber + 1}</a>
                                        </li>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="startPage" value="${pessoas.number - 2}" />
                                    <c:set var="endPage" value="${pessoas.number + 2}" />
                                    <c:if test="${startPage < 0}">
                                        <c:set var="startPage" value="0" />
                                        <c:set var="endPage" value="4" />
                                    </c:if>
                                    <c:if test="${endPage >= pessoas.totalPages}">
                                        <c:set var="startPage" value="${pessoas.totalPages - 5}" />
                                        <c:set var="endPage" value="${pessoas.totalPages - 1}" />
                                    </c:if>
                                    <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                        <c:set var="pageNumber" value="${i}" />
                                        <li class="page-item ${pessoas.number == i ? 'active' : ''}">
                                            <a class="page-link" href="/jsp/pessoas?page=${pageNumber}">${pageNumber + 1}</a>
                                        </li>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                            <li class="page-item ${pessoas.last ? 'disabled' : ''}">
                                <a class="page-link" href="/jsp/pessoas?page=${pessoas.number + 1}" aria-label="Próximo">Próximo</a>
                            </li>
                        </ul>
                    </div>
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
