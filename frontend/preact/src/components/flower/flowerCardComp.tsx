import { CreateBouquetFlower, Flower } from "@api/api"
import { useEffect, useState } from "preact/hooks"
import { useBouquet } from "../../pages/Home/Provider/bouquetFlowerProvider";
import FlowerCardFront from "./flowerCardFront";
import FlowerCardBack from "./flowerCardBack";

export default function FlowerCard(prop: { flower: Flower, selected: boolean }) {
  const [front, setFront] = useState(true);
  const { flower } = prop
  const context = useBouquet();
  const [bouquetFlower, setBouquetFlower] = useState<CreateBouquetFlower>({ flowerId: flower.id, quantity: 0 });
  const sideFlip = (front) => {
    if (front) {
      return {
        transform: 'rotateY(0)',
        transition: "all 0.3s",
        opacity: 1,
        zIndex: 10,
        boxShadow: '0 20px 50px rgba(0,0,0,0.5)'
      }
    }
    else {
      return {
        transform: 'rotateY(180deg)',
        transition: "all 0.3s",
        opacity: 1,
        zIndex: 10,
        boxShadow: '0 20px 50px rgba(0,0,0,0.5)'
      }
    }
  }

  useEffect(() => {
    if (!prop.selected) {
      setFront(true);
    }
  })
  const addToCart = () => {
    if (bouquetFlower.quantity > 0 && bouquetFlower.quantity <= flower.quantity) {

      context.addToBouquet(bouquetFlower)
      setBouquetFlower({ flowerId: flower.id, quantity: 0 });
    }
  }


  const incrementQuantity = () => {
    if (bouquetFlower.quantity <= flower.quantity) {
      setBouquetFlower({ flowerId: bouquetFlower.flowerId, quantity: ++bouquetFlower.quantity })
    }
  }
  const decrementQuantity = () => {
    if (bouquetFlower.quantity > 0) {
      setBouquetFlower({ flowerId: bouquetFlower.flowerId, quantity: --bouquetFlower.quantity })
    }
  }


  return (
    <>
      {front ? (
        <div style={{ ...sideFlip(front) }}>
          <div style={{ transform: "rotateY(0deg)" }}>
            <FlowerCardFront
            selected={prop.selected}
            flower={flower}
            switch={() => setFront(!front)} />
          </div>
        </div>
      ) : (
        <div hidden={front} style={{ ...sideFlip(front) }}>
          <div style={{ transform: "rotateY(180deg)" }}>

            <FlowerCardBack
            switch={() => setFront(!front)}
            flower={flower}
            bouquetFlower={bouquetFlower}
            addToCart={() => addToCart()}
            decrementQuantity={() => decrementQuantity()}
            incrementQuantity={() => incrementQuantity()}
            />
          </div>
        </div>
      )}
    </>
  );
};