import { Box, Fab } from '@mui/material';
import FlowerCarousel from '../../components/flower/flowerCarousel';
import { BouquetFlowerProvider } from './Provider/bouquetFlowerProvider';
import Cart from '../../components/widget/cart';
import BillCarousel from '../../components/bill/billCarousel';
import { DataProvider } from './Provider/dataProvider';
import { DarkMode, LightMode, PersonOutlined } from '@mui/icons-material';
import BurstLayer from './fx/burstLayer';
import { useEffect, useState } from 'preact/hooks';
import "./style.css"
import SessionToast from "./fx/sessionToast";

export function Home({mode, setMode}) {

  const [localMode, setLocalMode] = useState(mode);
  const [adminMode, setAdminMode] = useState(false);

useEffect(() => {
  setLocalMode(mode);
}, [mode]);

  return (
    <>
   <DataProvider>
  <BouquetFlowerProvider>
    <Cart />

    {/* Flower Section */}
    <Box
      sx={{
       width: '100%',

    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',

    boxSizing: 'border-box',

    paddingTop: {
      xs: '10rem',
      sm: '2.5rem',
      md: '4rem',
    },

    paddingBottom: {
      xs: '10rem',
      sm: '2.5rem',
      md: '4rem',
    },
      }}
    >
      <FlowerCarousel adminMode={adminMode} />
    </Box>

    {/* Bill Section */}
    <Box
      sx={{
         width: '100%',

    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',

    boxSizing: 'border-box',

   paddingTop: {
      xs: '10rem',
      sm: '2.5rem',
      md: '4rem',
    },

    paddingBottom: {
      xs: '10rem',
      sm: '2.5rem',
      md: '4rem',
    },
      }}
    >
      <BillCarousel />
    </Box>

  </BouquetFlowerProvider>

  <BurstLayer />
       <SessionToast />
</DataProvider>
 <Fab
  sx={{
    position: 'fixed',
    bottom: 24,
    left: 24,
    zIndex: 9999,
  }}
  color="primary"
  onClick={() => {
    const next =
      localMode === 'light'
        ? 'dark'
        : 'light';

    setLocalMode(next);
    setMode(next);
  }}
>
  {localMode === 'light'
    ? <DarkMode />
    : <LightMode />}
</Fab>

        <Fab
            color={adminMode ? "secondary" : "default"}
            onClick={() => setAdminMode(v => !v)}
            sx={{
                position: "fixed",
                top: 24,
                left: 24,
                zIndex: 9999,
            }}
        >
            <PersonOutlined />
        </Fab>
</>


  );
}


