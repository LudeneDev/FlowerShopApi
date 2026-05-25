import { useState } from 'preact/hooks';
import styles from './carousel.module.css';
import { Box, Grid, IconButton } from '@mui/material';
import ArrowBack from '@mui/icons-material/ArrowBack';
import ArrowForward from '@mui/icons-material/ArrowForward';
import CarouselItem from './carouselItem';
import IndicatorBar from "./indicatorBar";

interface CarouselProps<T> {
  data: T[];
  renderItem: (item: T, index: number, isActive : boolean) => any;
}

export default function Carousel<T>({ data, renderItem }: CarouselProps<T>) {
  const [activeIndex, setActiveIndex] = useState(0);



  const prev = () => setActiveIndex(Math.max(0, activeIndex - 1));
  const next = () => setActiveIndex(Math.min(data.length - 1, activeIndex + 1));





  return (
      <>
 <div className={styles.carousel}>

    {/* LEFT ARROW */}
    <IconButton
      onClick={prev}
      color='primary'
      sx={{ flexShrink: 0 }} // Just a tiny MUI tweak, rest is CSS
    >
      <ArrowBack sx={{ fontSize: { xs: '1.5rem', md: '2.5rem' }, zIndex: 50 }} />
    </IconButton>

    {/* THE CARD AREA */}
    <div className={styles.cardArea}>
      {data.length !== 0 ? (
        data.map((item, index) => (
          <CarouselItem key={index} index={index} activeIndex={activeIndex}>
            {renderItem(item, index, (index === activeIndex))}
          </CarouselItem>
        ))
      ) : (
        <CarouselItem key="empty" index={0} activeIndex={0}>
          {renderItem(null, 0, true)}
        </CarouselItem>
      )}
    </div>

    {/* RIGHT ARROW */}
    <IconButton
      onClick={next}
      color='primary'
      sx={{ flexShrink: 0 }}
    >
      <ArrowForward sx={{ fontSize: { xs: '1.5rem', md: '2.5rem' }, zIndex: 50 }} />
    </IconButton>



  </div>

        <IndicatorBar data={data} activeIndex={activeIndex} setActiveIndex={setActiveIndex} />
  </>
  );
}

