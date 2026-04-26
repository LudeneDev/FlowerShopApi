
import { Badge, IconButton } from '@mui/material';
import { ShoppingCart } from '@mui/icons-material';
import { useState } from 'preact/hooks';
import ListDrawer from './listDrawer';
import { useBouquet } from '../../pages/Home/Provider/bouquetFlowerProvider';
import { useDataContext } from '../../pages/Home/Provider/dataProvider';

export default function Cart() {
  const context = useBouquet();
  const [open, setOpen] = useState(false);
  const { flowers } = useDataContext();


  return (
    <div style={{ position: "sticky", top: "0", display: "flex", justifyContent: "end", padding: "1rem" }}>
      <Badge sx={{ padding: "0" }} badgeContent={context.orders.length} color="secondary">
        <IconButton onClick={() => setOpen(!open)}>
          <ShoppingCart color="primary" fontSize="medium" />
        </IconButton>
      </Badge>

      <ListDrawer
        open={open}
        setOpen={setOpen}
        flowers={flowers}
        context={context}
      />

    </div>

  );
}