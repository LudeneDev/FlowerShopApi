import { LocationProvider, Router, Route, hydrate, prerender as ssr } from 'preact-iso';


import { Home } from './pages/Home';
import { NotFound } from './pages/_404.js';
import './style.css';
import { ThemeProvider } from '@mui/material/styles';
import getTheme from './theme.ts';
import { CssBaseline } from '@mui/material';
import { useEffect, useMemo, useState } from 'preact/hooks';

export function App({mode, setMode}) {
	return (
		<LocationProvider>
			<main>
				<Router>
					<Route
						path="/"
						component={() => (
							<Home mode={mode} setMode={setMode} />
						)}
					/>

					<Route default component={NotFound} />
				</Router>
			</main>
		</LocationProvider>
	);
}

if (typeof window !== 'undefined') {
	hydrate(<Root />, document.getElementById('app'));
}

export async function prerender(data) {
	return await ssr(<App {...data} />);
}

function Root() {





const getInitialMode = () => {
  const saved = localStorage.getItem('theme-mode');
  if (saved === 'dark' || saved === 'light') return saved;
  return window.matchMedia('(prefers-color-scheme: dark)').matches
    ? 'dark'
    : 'light';
};

const [mode, setMode] = useState<'light' | 'dark'>(
  getInitialMode()
);




	useEffect(() => {
		localStorage.setItem('theme-mode', mode);
	}, [mode]);

	const theme = useMemo(() => getTheme(mode), [mode]);

	return (
		<ThemeProvider theme={theme}>
			<CssBaseline />

			<App mode={mode} setMode={setMode} />
		</ThemeProvider>
	);
}