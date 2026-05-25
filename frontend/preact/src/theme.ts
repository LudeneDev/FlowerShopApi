import { createTheme } from '@mui/material/styles';


export const getTheme = (mode: "light" | "dark") =>
  createTheme({
    palette: {
      mode,

      primary: {
        main: '#35c500ff',
        light: '#FFEBEE',
        dark: '#00cc0aff',
        contrastText: '#ffffff',
      },

      secondary: {
        main: '#19857b',
      },

      background: {
        default: mode === 'dark' ? '#121212' : '#ffffff',
        paper: mode === 'dark' ? '#1e1e1e' : '#ffffff',
      },
    },
  });

export default getTheme;