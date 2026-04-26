import { LocationProvider, Router, Route, hydrate, prerender as ssr } from 'preact-iso';


import { Home } from './pages/Home';
import { NotFound } from './pages/_404.js';
import './style.css';
import { ThemeProvider } from '@mui/material/styles';
import theme from './theme';
import { CssBaseline } from '@mui/material';

export function App() {
	return (
		<LocationProvider>


			<main>
				<Router>
					<Route path="/" component={Home} />
					<Route default component={NotFound} />
				</Router>
			</main>

		</LocationProvider>
	);
}

if (typeof window !== 'undefined') {
	hydrate(<ThemeProvider theme={theme}><CssBaseline /><App /></ThemeProvider>, document.getElementById('app'));
}

export async function prerender(data) {
	return await ssr(<App {...data} />);
}
