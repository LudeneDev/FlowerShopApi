import { Box } from '@mui/material';
import FlowerCarousel from '../../components/flower/flowerCarousel';
import { BouquetFlowerProvider } from './Provider/bouquetFlowerProvider';
import Cart from '../../components/widget/cart';
import BillCarousel from '../../components/bill/billCarousel';
import { DataProvider } from './Provider/dataProvider';

export function Home() {

  return (
    <DataProvider>
      <BouquetFlowerProvider>
        <Cart />
        <Box
          sx={{
            alignItems: 'center',
            minHeight: '100vh',
            margin: 0,
            minWidth: '100vw',
          }}
        >
          <FlowerCarousel />
        </Box>
        <Box
          sx={{
            alignItems: 'center',
            minHeight: '100vh',
            margin: 0,
            minWidth: '100vw'
          }}>
          <BillCarousel />
        </Box>
      </BouquetFlowerProvider>
    </DataProvider>
  );
}


