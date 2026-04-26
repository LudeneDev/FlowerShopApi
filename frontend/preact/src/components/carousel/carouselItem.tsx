import { Box } from '@mui/material';

interface CarouselItemProps {
  index: number;
  activeIndex: number;
  children: any;
  width?: string;
}

export default function CarouselItem({
  index,
  activeIndex,
  children,
  width = '33%'
}: CarouselItemProps) {

  const getTransform = () => {
    if (index === activeIndex) {
      return 'translateX(0) scale(1)';
    }
    if (index === activeIndex - 1) {
      return 'translateX(-70%) rotateY(-45deg) scale(0.85)';
    }
    if (index === activeIndex + 1) {
      return 'translateX(70%) rotateY(45deg) scale(0.85)';
    }
    return 'scale(0)';
  };

  const isActive = index === activeIndex;

  return (
    <Box
      sx={{
        position: 'absolute',
        width: width,
        transition: 'all 0.6s cubic-bezier(0.23, 1, 0.32, 1)',
        opacity: isActive ? 1 : 0.6,
        zIndex: isActive ? 10 : 5,
        transform: getTransform(),
        '& > *': {
            width: '100%',
            height: '100%',
            boxSizing: 'border-box'
        }
      }}
    >
      {children}
    </Box>
  );
}