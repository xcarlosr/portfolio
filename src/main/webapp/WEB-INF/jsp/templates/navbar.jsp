<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<ul class="navbar-nav mr-auto">
		<li class="nav-item">
			<a class="nav-link ${param.activeNavItem == 'home' ? 'active' : ''}" href="/">Home</a>
		</li>
		<li class="nav-item">
			<a class="nav-link ${param.activeNavItem == 'projetos' ? 'active' : ''} " href="/jsp/projetos">Projetos</a>
		</li>
		<li class="nav-item">
			<a class="nav-link ${param.activeNavItem == 'pessoas' ? 'active' : ''}" href="/jsp/pessoas">Pessoas</a>
		</li>
		<li class="nav-item">
			<a class="nav-link ${param.activeNavItem == 'membros' ? 'active' : ''}" href="/jsp/projetos/1/membros">Membros</a>
		</li>
	</ul>
</nav>
