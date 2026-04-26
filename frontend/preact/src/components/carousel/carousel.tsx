import { useState } from 'preact/hooks';
import { Grid, IconButton } from '@mui/material';
import ArrowBack from '@mui/icons-material/ArrowBack';
import ArrowForward from '@mui/icons-material/ArrowForward';
import CarouselItem from './carouselItem';

interface CarouselProps<T> {
  data: T[];
  renderItem: (item: T, index: number, isActive : boolean) => any;
}

export default function Carousel<T>({ data, renderItem }: CarouselProps<T>) {
  const [activeIndex, setActiveIndex] = useState(0);



  const prev = () => setActiveIndex(Math.max(0, activeIndex - 1));
  const next = () => setActiveIndex(Math.min(data.length - 1, activeIndex + 1));




  return (
    <Grid
      container
      columns={{ xs: 4, sm: 12 }}
      sx={{
        height: '100vh',
        alignItems: 'center',
        justifyContent: 'center'
      }}
    >
      {/* Left Arrow */}
      <Grid size={1} sx={{ display: 'flex', justifyContent: 'center' }}>
        <IconButton size="large" onClick={prev} color='primary'>
          <ArrowBack fontSize="large" />
        </IconButton>
      </Grid>

      {/* Center Stage */}
      <Grid
        size={{ xs: 2, sm: 10 }}
        sx={{ position: 'relative', height: '100%', display: 'flex', alignItems: 'center', justifyContent: 'center' }}
      >
        {data.map((item, index) => (

          <CarouselItem
            key={index}
            index={index}
            activeIndex={activeIndex}
          >
            {renderItem(item, index, (index === activeIndex))}
          </CarouselItem>
        ))}
      </Grid>

      {/* Right Arrow */}
      <Grid size={1} sx={{ display: 'flex', justifyContent: 'center' }}>
        <IconButton size="large" onClick={next} color='primary'>
          <ArrowForward fontSize="large" />
        </IconButton>
      </Grid>
    </Grid>
  );
}