import { Box, useMediaQuery, useTheme } from '@mui/material';


type CarouselItemProps = {
  children: React.ReactNode;
  activeIndex: number;
  index: number;
};

export default function CarouselItem({
  children,
  activeIndex,
  index,
}: CarouselItemProps) {



    const distance = index - activeIndex;
    const abs = Math.abs(distance);

    const isActive = distance === 0;
    const isVisible = abs <= 1;

    return (
        <Box
            sx={{
                position: 'absolute',

                width: {
                    xs: '92%',
                    sm: '80%',
                    md: '65%',
                    lg: '50%',
                    xl: '42%',
                },

                maxWidth: '900px',
                height: '100%',

                transition:
                    'transform 0.6s cubic-bezier(0.23, 1, 0.32, 1), opacity 0.4s',

                transform: `
        translateX(${distance * 80}%)
        scale(${isActive ? 1 : 0.85})
        rotateY(${distance === 0 ? 0 : distance < 0 ? 25 : -25}deg)
      `,

                opacity: isVisible ? (isActive ? 1 : 0.6) : 0,
                pointerEvents: isVisible ? 'auto' : 'none',

                zIndex: isActive ? 10 : 5,
                '& > *': {
                    width: '100%',
                    height: '100%',
                },
            }}
        >
            {children}
        </Box>
    );
}





