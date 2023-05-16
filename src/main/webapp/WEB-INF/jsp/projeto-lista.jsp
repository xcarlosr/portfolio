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
								<a class="nav-link active" href="/jsp/projetos">Projetos</a>
							</li>
							<li class="nav-item">
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
                          <h3>Projetos</h3>
                          <div class="col d-flex align-items-center ml-auto">
                              <div class="input-group">
                                  <input type="text" id="campoPesquisar" class="form-control" placeholder="Pesquisa por ID, Nome ou Descrição do Projeto">
                                  <div class="input-group-append ml-2">
                                      <button class="btn btn-primary" id="btnPesquisar" type="button">
                                          <i class="fas fa-search"></i> Pesquisar
                                      </button>
                                  </div>
                              </div>
                          </div>
                        </div>
                        <div class="col d-flex justify-content-end">
                          <button type="button" class="btn btn-primary bt-sm adicionar-projeto pl-10" 
                          style="padding-right: 5px; margin-top: 5px; margin-bottom: 5px;  margin-right: 5px;">
                           <i class="fas fa-plus"></i> Adicionar
                        </button>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table id="tabela-projetos" class="table table-striped">
                            <thead>
                                <tr>
                                    <th style="width: 10px;">ID</th>
                                    <th style="width: 10%;">Nome</th>
                                    <th style="width: 10%;">Gerente</th>
                                    <th style="width: 7%;">Início</th>
                                    <th style="width: 7%;">Prev. Fim</th>
                                    <th style="width: 7%;">Fim</th>
                                    <th style="width: 30%;">Descricao</th>
                                    <th style="width: 10%;">Status</th>
                                    <th style="width: 5%;">Orcamento</th>
                                    <th style="width: 5%;">Risco</th>
                                    <th style="width: 12%;">Ações</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="projeto" items="${projetos.content}">  
                                    <tr id="linha-projeto-${projeto.id}">
                                        <td>${projeto.id}</td>
                                        <td>${projeto.nome}</td>
                                        <td id="nomeGerente">${projeto.gerente.nome}</td>
                                        <td>${projeto.dataInicio}</td>
                                        <td>${projeto.dataPrevisaoFim}</td>
                                        <td>${empty projeto.dataFim ? '-' : projeto.dataFim}</td>
                                        <td>${projeto.descricao}</td>
                                        <td>${projeto.status}</td>
                                        <td>
                                            <fmt:formatNumber value="${projeto.orcamento != null ? projeto.orcamento : 0}" type="currency" currencyCode="BRL" />
                                        </td>
                                        <td>${projeto.risco}</td>
                                        <td>
                                            <a href="#" style="display: inline-flex;" idProjeto="${projeto.id}" class="btn btn-default editar-projeto" data-toggle="modal">
                                                <i class="fas fa-pencil-alt"></i>
                                            </a>
                                            <a href="#" style="display: inline-flex;" idProjeto="${projeto.id}" class="btn btn-default deletar-projeto" data-toggle="modal">
                                                <i class="fas fa-trash-alt"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div style="padding-right: 13px;" class="row justify-content-end">
                        <ul class="pagination">
                            <li class="page-item ${projetos.first ? 'disabled' : ''}">
                                <a class="page-link" href="/jsp/projetos?page=${projetos.number - 1}" aria-label="Anterior">Anterior<a>
                            </li>
                            <c:choose>
                                <c:when test="${projetos.totalPages <= 5}">
                                    <c:forEach var="i" begin="0" end="${projetos.totalPages - 1}">
                                        <c:set var="pageNumber" value="${i}" />
                                        <li class="page-item ${projetos.number == i ? 'active' : ''}">
                                            <a class="page-link" href="/jsp/projetos?page=${pageNumber}">${pageNumber + 1}</a>
                                        </li>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="startPage" value="${projetos.number - 2}" />
                                    <c:set var="endPage" value="${projetos.number + 2}" />
                                    <c:if test="${startPage < 0}">
                                        <c:set var="startPage" value="0" />
                                        <c:set var="endPage" value="4" />
                                    </c:if>
                                    <c:if test="${endPage >= projetos.totalPages}">
                                        <c:set var="startPage" value="${projetos.totalPages - 5}" />
                                        <c:set var="endPage" value="${projetos.totalPages - 1}" />
                                    </c:if>
                                    <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                        <c:set var="pageNumber" value="${i}" />
                                        <li class="page-item ${projetos.number == i ? 'active' : ''}">
                                            <a class="page-link" href="/jsp/projetos?page=${pageNumber}">${pageNumber + 1}</a>
                                        </li>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                            <li class="page-item ${projetos.last ? 'disabled' : ''}">
                                <a class="page-link" href="/jsp/projetos?page=${projetos.number + 1}" aria-label="Próximo">Próximo</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="footer">
                    
                </div>
            </div>
        </div>
    </div>

<!-- Pop-up do formulário -->
<div class="modal fade" id="projetoModal" tabindex="-1" role="dialog" aria-labelledby="projetoModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="projetoModalLabel">Editar Projeto</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="modalProjetoForm">
                    <div class="form-group" id="idProjetoFormGroup">
                        <label for="idProjetoForm">Id:</label>
                        <input type="text" class="form-control" id="idProjetoForm" readonly/>        
                    </div>
                    <div class="form-group">
                        <label for="nomeProjeto">Nome do Projeto:</label>
                        <input type="text" class="form-control" id="nomeProjeto" required>
                        <div class="invalid-feedback">O campo Nome do Projeto é obrigatório.</div>
                    </div>
                    <div class="form-group">
                        <label for="pessoasList">Gerente:</label>
                        <select class="form-control" id="pessoasList"></select>
                    </div>
                    <div class="form-group">
                        <label for="dtInicio">Início:</label>
                        <input type="text" class="form-control" id="dtInicio" data-mask="00/00/0000">
                    </div>
                    <div class="form-group">
                        <label for="dtPrevisaoFim">Previsão de Fim:</label>
                        <input type="text"  class="form-control" id="dtPrevisaoFim" data-mask="00/00/0000">
                    </div>
                    <div class="form-group">
                        <label for="dtFim">Data Fim:</label>
                        <input type="text"  class="form-control" id="dtFim" data-mask="00/00/0000">
                    </div>
                    <div class="form-group">
                        <label for="descProjeto">Descrição:</label>
                        <input type="text"  class="form-control" id="descProjeto">
                    </div>
                    <div class="form-group">
                        <label for="statusProjeto">Status:</label>
                        <select class="form-control" id="statusProjeto">
                            <option value="EM_ANALISE">Em Análise</option>
                            <option value="ANALISE_REALIZADA">Análise Realizada</option>
                            <option value="ANALISE_APROVADA">Análise Aprovada</option>
                            <option value="INICIADO">Iniciado</option>
                            <option value="PLANEJADO">Planejado</option>
                            <option value="EM_ANDAMENTO">Em Andamento</option>
                            <option value="ENCERRADO">Encerrado</option>
                            <option value="CANCELADO">Cancelado</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="orcProjeto">Orçamento:</label>
                        <input type="text"  class="form-control" id="orcProjeto">
                    </div>
                    <div class="form-group">
                        <label for="riscoProjeto">Risco:</label>
                        <select class="form-control" id="riscoProjeto">
                            <option value="BAIXO_RISCO">Baixo Risco</option>
                            <option value="MEDIO_RISCO">Médio Risco</option>
                            <option value="ALTO_RISCO">Alto Risco</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="salvarButton">Salvar</button>
                <button type="button" class="btn btn-primary" id="editarButton">Atualizar</button>
                <button type="button" class="btn btn-primary" id="deletarButton">Deletar</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
            </div>
        </div>
    </div>
</div>


<script>
    $(document).ready(function() {

        //Chama o modal para salva novo projeto.
        $('.adicionar-projeto').click(function() {
            event.preventDefault();
            
            var acao = 'salvar';
            limpaValorProjetoModal(acao);
            carregarListBoxGerente(acao);
            habilarBotoes(acao);
            
            $('#projetoModal').modal('show');

        });    
        

        // Prepara o modal para editar os valores.
        $('.editar-projeto').click(function(event) {
            event.preventDefault();
            
            var projetoId = $(this).attr('idProjeto');
            var linhaProjeto = $('#linha-projeto-' + projetoId);
            
            var acao = 'editar';
            limpaValorProjetoModal(acao);
            habilarBotoes(acao);
            setarDadosFormProjeto(acao, projetoId, linhaProjeto);

            $('#projetoModal').modal('show');

        });

        // Prepara o modal para editar os valores.
        $('.deletar-projeto').click(function(event) {
            event.preventDefault();

            var projetoId = $(this).attr('idProjeto');
            var linhaProjeto = $('#linha-projeto-' + projetoId);
            
            var acao = 'deletar';
            limpaValorProjetoModal(acao);
            setarDadosFormProjeto(acao, projetoId, linhaProjeto);
            habilarBotoes(acao);

            $('#projetoModal').modal('show');

        });

        $('#btnPesquisar').click(function() {
            event.preventDefault();

            var paramPesquisar = $('#campoPesquisar').val().trim();
            pesquisarProjetos(paramPesquisar);
        });
        
        $('#salvarButton').click(function(event){
            event.preventDefault();

            if ($('#nomeProjeto').val().trim() === '') {
                valicarCamposObrigatorios()
                return false;
            }

            var jsonData = pegaValoresProjetoModal();   

            enviarForm('POST', '/projetos', jsonData);
                       
            $('#projetoModal').modal('hide');
        });

        $('#editarButton').click(function(event){
            event.preventDefault();

            if ($('#nomeProjeto').val().trim() === '') {
                valicarCamposObrigatorios()
                return false;
            }

            var jsonData = pegaValoresProjetoModal();   

            var url = '/projetos/' + jsonData.id;

            enviarForm('PUT', url, jsonData);
                       
            $('#projetoModal').modal('hide');

        });


        $('#deletarButton').click(function(event){
            event.preventDefault();

            var jsonData = pegaValoresProjetoModal();   

            var url = '/projetos/' + jsonData.id;

            enviarForm('DELETE', url, jsonData);
                       
            $('#projetoModal').modal('hide');

        });
        
        function enviarForm(type, url, jsonData){

            $.ajax({
                url: url,
                type: type,
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(jsonData),
                success: function(data) {
                    new Swal({
                        title: 'Sucesso!',
                        text: data.message,
                        icon: 'success'
                    });
                },
                error: function(xhr, textStatus, errorThrown) {
                    
                    console.log(xhr.status);
                    console.log(xhr.responseText);
                    console.log(errorThrown);
                    
                    var response = JSON.parse(xhr.responseText);

                    new Swal({
                        title: "Erro",
                        text: response.message,
                        icon: "error"
                    });
                    
                }
            });

        }

        // Valida o campo nome do projeto
        $('#nomeProjeto').on('blur', function() {
            event.preventDefault();
            valicarCamposObrigatorios(this);
        });

        function valicarCamposObrigatorios(formModal){
            if ($('#nomeProjeto').val().trim().length < 3) {
                $('#nomeProjeto').addClass('is-invalid');
                
                if ($('#salvarButton').is(':visible')) {
                    $('#salvarButton').prop('disabled', true);
                } else if ($('#editarButton').is(':visible')) {
                    $('#editarButton').prop('disabled', true);
                }
                return false;
            } 

            $(formModal).removeClass('is-invalid');

            if ($('#salvarButton').is(':visible')) {
                $('#salvarButton').prop('disabled', false);
            } else if ($('#editarButton').is(':visible')) {
                $('#editarButton').prop('disabled', false);
            }
        }

        function setarDadosFormProjeto(acao, projetoId, linhaProjeto){

            var nomeProjeto = linhaProjeto.find('td:nth-child(2)').text();
            var nomeGerente = linhaProjeto.find('#nomeGerente').text();
            var dtInicioProjeto = linhaProjeto.find('td:nth-child(4)').text();
            var dtPrevisaoFimProjeto = linhaProjeto.find('td:nth-child(5)').text();
            var dtFimProjeto = linhaProjeto.find('td:nth-child(6)').text();
            var descricaoProjeto = linhaProjeto.find('td:nth-child(7)').text();
            var statusProjeto = linhaProjeto.find('td:nth-child(8)').text();
            var orcamentoProjeto = linhaProjeto.find('td:nth-child(9)').text().trim();
            var riscoProjeto = linhaProjeto.find('td:nth-child(10)').text();
            
            carregarListBoxGerente(acao, nomeGerente);
            
            if(acao === 'editar') {
                $('#idProjetoForm').val(projetoId);
                $('#nomeProjeto').val(nomeProjeto);
                $('#dtInicio').val(dtInicioProjeto.trim() != "-" ? dtInicioProjeto: '');
                $('#dtPrevisaoFim').val(dtPrevisaoFimProjeto.trim() != "-" ? dtPrevisaoFimProjeto: '');
                $('#dtFim').val(dtFimProjeto.trim() != "-" ? dtFimProjeto: '');
                $('#descProjeto').val(descricaoProjeto);
                $('#statusProjeto').val(statusProjeto);
                $('#orcProjeto').val(orcamentoProjeto);
                $('#riscoProjeto').val(riscoProjeto);
            } else {
                $('#idProjetoForm').val(projetoId);
                $('#nomeProjeto').val(nomeProjeto).prop('readonly', true);
                $('#dtInicio').val(dtInicioProjeto).prop('readonly', true);
                $('#dtPrevisaoFim').val(dtPrevisaoFimProjeto).prop('readonly', true);
                $('#dtFim').val(dtFimProjeto.trim() != "-" ? dtFimProjeto: '').prop('readonly', true);
                $('#descProjeto').val(descricaoProjeto).prop('readonly', true);
                $('#statusProjeto').val(statusProjeto).prop('disabled', true);
                $('#orcProjeto').val(orcamentoProjeto).prop('readonly', true);
                $('#riscoProjeto').val(riscoProjeto).prop('disabled', true);
            }

        }

        function carregarListBoxGerente(acao, nomeGerente) {
            $.ajax({
                url: '/jsp/pessoas/lista',
                type: 'GET',
                dataType: 'json',
                contentType: 'application/json',
                success: function(pessoas) {
                    var selectPessoas = $('#pessoasList');
                    selectPessoas.empty();
                    pessoas.forEach(function(pessoa) {
                        var option = $('<option>', {
                            value: pessoa.id,
                            text: pessoa.nome
                        });
                                                
                        if (acao !== 'salvar' && pessoa.nome === nomeGerente) {
                            option.prop('selected', true);
                        }
                        
                        if(acao === 'deletar') {
                            selectPessoas.prop('disabled', true);
                        }

                        selectPessoas.append(option);
                    });
                },
                error: function(error) {
                    console.log('Ocorreu um erro na chamada AJAX listar pessoas');
                    console.log(error);
                }
            });

        }


        function pegaValoresProjetoModal(){

            var idProjeto = $('#idProjetoForm').val() != '' ? $('#idProjetoForm').val() : null;
            var nomeProjeto = $('#nomeProjeto').val();
            var idGerenteProjeto = $('#pessoasList').val();
            
            var dtInicioProjeto = formatarData($('#dtInicio').val());
            var dtPrevisaoFimProjeto = formatarData($('#dtPrevisaoFim').val());
            var dtFimProjeto = formatarData($('#dtFim').val());

            var descricaoProjeto = $('#descProjeto').val();
            var statusProjeto = $('#statusProjeto').val();
            var orcamentoProjeto = $('#orcProjeto').val() != '' ? parseFloat($('#orcProjeto').maskMoney('unmasked')[0]).toFixed(2) : null;
            var riscoProjeto = $('#riscoProjeto').val();


            var projeto = {
                id: idProjeto,
                nome: nomeProjeto,
                dataInicio: dtInicioProjeto,
                dataPrevisaoFim: dtPrevisaoFimProjeto,
                dataFim: dtFimProjeto,
                descricao: descricaoProjeto,
                status: statusProjeto,
                orcamento: orcamentoProjeto,
                risco: riscoProjeto,
                idGerente: idGerenteProjeto
            }    

            return projeto;
        }


        function pesquisarProjetos(paramPesquisar) {
            $.ajax({
                url: '/jsp/projetos/pesquisar',
                type: 'GET',
                dataType: 'json',
                contentType: 'application/json',
                data: {
                    valor: paramPesquisar
                },
                success: function(data) {
                    atualizarTabelaProjetos(data);
                },
                error: function(xhr, textStatus, errorThrown) {
                    console.log(xhr.status);
                    console.log(xhr.responseText);
                    console.log(errorThrown);
                }
            });
        }

        function atualizarTabelaProjetos(data) {
            var tabela = $('#tabela-projetos tbody');

            if(data != null) {
                tabela.empty();

                data.content.forEach(function (projeto) {
                    var linha = $('<tr>', { id: 'linha-projeto-' + projeto.id });
                    linha.append($('<td>').text(projeto.id));
                    linha.append($('<td>').text(projeto.nome));
                    linha.append($('<td>', { id: 'nomeGerente' }).text(projeto.gerente.nome));
                    linha.append($('<td>').text(projeto.dataInicio));
                    linha.append($('<td>').text(projeto.dataPrevisaoFim));
                    linha.append($('<td>').text(projeto.dataFim ? projeto.dataFim : '-'));
                    linha.append($('<td>').text(projeto.descricao));
                    linha.append($('<td>').text(projeto.status));
                        linha.append($('<td>').text(projeto.orcamento != null ? new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(projeto.orcamento) : '0'));
                    linha.append($('<td>').text(projeto.risco));

                    var acoes = $('<td>');
                    var editarLink = $('<a>', {
                        href: '#',
                        style: 'display: inline-flex;',
                        idProjeto: projeto.id,
                        class: 'btn btn-default editar-projeto',
                        'data-toggle': 'modal'
                    }).html('<i class="fas fa-pencil-alt"></i>');
                    editarLink.appendTo(acoes);

                    var deletarLink = $('<a>', {
                        href: '#',
                        style: 'display: inline-flex;',
                        idProjeto: projeto.id,
                        class: 'btn btn-default deletar-projeto',
                        'data-toggle': 'modal'
                    }).html('<i class="fas fa-trash-alt"></i>');
                    deletarLink.appendTo(acoes);

                    linha.append(acoes);
                    tabela.append(linha);
                });

                var pagination = $('.pagination');
                pagination.empty();

                var firstItem = $('<li>', { class: data.first ? 'page-item disabled' : 'page-item' });
                var firstLink = $('<a>', { class: 'page-link', href: '/jsp/projetos?page=' + (data.number - 1), 'aria-label': 'Anterior' }).text('Anterior');
                firstLink.appendTo(firstItem);
                firstItem.appendTo(pagination);

                if (data.totalPages <= 5) {
                    for (var i = 0; i < data.totalPages; i++) {
                        var pageNumber = i;
                        var pageItem = $('<li>', { class: data.number == i ? 'page-item active' : 'page-item' });
                        var pageLink = $('<a>', { class: 'page-link', href: '/jsp/projetos?page=' + pageNumber }).text(pageNumber + 1);
                        pageLink.appendTo(pageItem);
                        pageItem.appendTo(pagination);
                    }
                } else {
                    var startPage = data.number - 2;
                    var endPage = data.number + 2;

                    if (startPage < 0) {
                        startPage = 0;
                        endPage = 4;
                    }

                    if (endPage >= data.totalPages) {
                        startPage = data.totalPages - 5;
                        endPage = data.totalPages - 1;
                    }

                    for (var i = startPage; i <= endPage; i++) {
                        var pageNumber = i;
                        var pageItem = $('<li>', { class: data.number == i ? 'page-item active' : 'page-item' });
                        var pageLink = $('<a>', { class: 'page-link', href: '/jsp/projetos?page=' + pageNumber }).text(pageNumber + 1);
                        pageLink.appendTo(pageItem);
                        pageItem.appendTo(pagination);
                    }
                }

                var lastItem = $('<li>', { class: data.last ? 'page-item disabled' : 'page-item' });
                var lastLink = $('<a>', { class: 'page-link', href: '/jsp/projetos?page=' + (data.number + 1), 'aria-label': 'Próximo' }).text('Próximo');
                lastLink.appendTo(lastItem);
                lastItem.appendTo(pagination);

                // Chama o modal para editar os valores.
                $('.editar-projeto').click(function(event) {
                    event.preventDefault();
                    
                    var projetoId = $(this).attr('idProjeto');
                    var linhaProjeto = $('#linha-projeto-' + projetoId);
                    
                    var acao = 'editar';
                    limpaValorProjetoModal(acao);
                    habilarBotoes(acao);
                    setarDadosFormProjeto(acao, projetoId, linhaProjeto);

                    $('#projetoModal').modal('show');

                });

                // Chama o modal para deletar os valores.
                $('.deletar-projeto').click(function(event) {
                    event.preventDefault();

                    var projetoId = $(this).attr('idProjeto');
                    var linhaProjeto = $('#linha-projeto-' + projetoId);
                    
                    var acao = 'deletar';
                    limpaValorProjetoModal(acao);
                    setarDadosFormProjeto(acao, projetoId, linhaProjeto);
                    habilarBotoes(acao);

                    $('#projetoModal').modal('show');

                });
               

            }
        }


        // function atualizarPaginacao(data) {
        //     var pagination = $('.pagination');
        //     pagination.empty();

        //     var firstItem = $('<li>', { class: data.first ? 'page-item disabled' : 'page-item' });
        //     var firstLink = $('<a>', { class: 'page-link', href: '/jsp/projetos?page=' + (data.number - 1), 'aria-label': 'Anterior' }).text('Anterior');
        //     firstLink.appendTo(firstItem);
        //     firstItem.appendTo(pagination);

        //     if (data.totalPages <= 5) {
        //         for (var i = 0; i < data.totalPages; i++) {
        //             var pageNumber = i;
        //             var pageItem = $('<li>', { class: data.number == i ? 'page-item active' : 'page-item' });
        //             var pageLink = $('<a>', { class: 'page-link', href: '/jsp/projetos?page=' + pageNumber }).text(pageNumber + 1);
        //             pageLink.appendTo(pageItem);
        //             pageItem.appendTo(pagination);
        //         }
        //     } else {
        //         var startPage = data.number - 2;
        //         var endPage = data.number + 2;

        //         if (startPage < 0) {
        //             startPage = 0;
        //             endPage = 4;
        //         }

        //         if (endPage >= data.totalPages) {
        //             startPage = data.totalPages - 5;
        //             endPage = data.totalPages - 1;
        //         }

        //         for (var i = startPage; i <= endPage; i++) {
        //             var pageNumber = i;
        //             var pageItem = $('<li>', { class: data.number == i ? 'page-item active' : 'page-item' });
        //             var pageLink = $('<a>', { class: 'page-link', href: '/jsp/projetos?page=' + pageNumber }).text(pageNumber + 1);
        //             pageLink.appendTo(pageItem);
        //             pageItem.appendTo(pagination);
        //         }
        //     }

        //     var lastItem = $('<li>', { class: data.last ? 'page-item disabled' : 'page-item' });
        //     var lastLink = $('<a>', { class: 'page-link', href: '/jsp/projetos?page=' + (data.number + 1), 'aria-label': 'Próximo' }).text('Próximo');
        //     lastLink.appendTo(lastItem);
        //     lastItem.appendTo(pagination);
        // }
    

        function limpaValorProjetoModal(acao){

            if(acao === 'salvar'){
                $('#idProjetoFormGroup').hide();    
            }else{
                $('#idProjetoFormGroup').show();    
            }

            $('#idProjetoForm').val('');
            $('#nomeProjeto').val('').prop('readonly', false).removeClass('is-invalid');
            $('#nomeGerente').prop('readonly', false);
            $('#pessoasList').prop('disabled', false).empty();
            $('#dtInicio').val('').prop('readonly', false);
            $('#dtPrevisaoFim').val('').prop('readonly', false);
            $('#dtFim').val('').prop('readonly', false);
            $('#descProjeto').val('').prop('readonly', false);
            $('#statusProjeto').val(null).prop('disabled', false);
            $('#orcProjeto').val('').prop('readonly', false);
            $('#riscoProjeto').val(null).prop('disabled', false);
         
        }


        function habilarBotoes(acao) {
                $('#salvarButton').hide();
                $('#editarButton').hide();
                $('#deletarButton').hide();
            if(acao === 'salvar'){
                $('#salvarButton').show();
            } else if(acao === 'editar'){
                $('#editarButton').show();
            } else if(acao === 'deletar'){
                $('#deletarButton').show();
            }
        }

        

        function formatarData(data) {
            if (data === "" || isNaN(Date.parse(data))) {
                return null;
            } else {
                return moment(data, 'YYYY-MM-DD').format('YYYY-MM-DD');
            }
        }

        $('#orcProjeto').maskMoney({
            prefix: 'R$ ',
            thousands: '.',
            decimal: ',',
            allowNegative: false,
            allowZero: false
        });

    });
        
</script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-maskmoney/3.0.2/jquery.maskMoney.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.18/dist/sweetalert2.js"></script>

</body>
</html>
