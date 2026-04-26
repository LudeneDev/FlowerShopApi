import { Button, Typography, Box } from '@mui/material';

export default function Checkout({ totalPrice, orderCount, onCheckout }: any) {
  return (
    <Box sx={{
      p: 2,
      borderTop: '1px solid #ddd',
      mt: 'auto',
      backgroundColor: 'background.paper'
    }}>
      <Typography variant="h6" sx={{ textAlign: 'right' }}>
        Total: ${totalPrice.toFixed(2)}
      </Typography>
      <Button
        variant="contained"
        fullWidth
        disabled={orderCount === 0}
        onClick={onCheckout}
      >
        Checkout
      </Button>
    </Box>
  );
}