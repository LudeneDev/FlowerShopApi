import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      main: '#35c500ff',
      light: '#FFEBEE',
      dark: '#00cc0aff',
      contrastText: '#ffffff',
    },
    secondary: {
      main: '#19857b', // A nice Blue
    },
  },
});

export default theme;