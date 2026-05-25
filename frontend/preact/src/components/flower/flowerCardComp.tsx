import { CreateBouquetFlower, Flower } from "@api/api"
import styles from "./flowerCard.module.css"
import { useEffect, useRef, useState } from "preact/hooks"
import { useBouquet } from "../../pages/Home/Provider/bouquetFlowerProvider";
import FlowerCardFront from "./flowerCardFront";
import FlowerCardBack from "./flowerCardBack";
import {FlowerBouncerApi} from "./flowerBouncer"
import { burstBus } from "../../pages/Home/fx/burstBus";
import getFlowerEmoji from "./flowerEmoji";


export default function FlowerCard(prop: { flower: Flower, selected: boolean }) {
  const [front, setFront] = useState(true);
  const { flower } = prop
  const emoji = getFlowerEmoji(flower.kind)
  const context = useBouquet();
  const [bouquetFlower, setBouquetFlower] = useState<CreateBouquetFlower>({ flowerId: flower.id, quantity: 0 });
   const orderDiff =
      context.orders.find(
          x => x.flowerId === flower.id
      )?.quantity || 0;

const fxRef = useRef<FlowerBouncerApi | null>(null)
const ref = useRef<HTMLDivElement>(null)


const initializedRef = useRef(false);

useEffect(() => {
  if (!initializedRef.current && fxRef.current) {

    fxRef.current.init(
      Math.min( flower.quantity, 64),
        emoji
    );

    initializedRef.current = true;


  }
}, []);




  useEffect(() => {

    if (!prop.selected) {
      setFront(true);

    }
  }, [prop.selected]);
  const addToCart = () => {
    if (bouquetFlower.quantity > 0 && bouquetFlower.quantity <= flower.quantity) {


      context.addToBouquet(bouquetFlower)

      setBouquetFlower({ flowerId: flower.id, quantity: 0 });

    }
  }
  const handleAddToCart = (e) => {

  const rect = e.currentTarget.getBoundingClientRect();

  burstBus.emit({
    x: rect.left + rect.width / 2,
    y: rect.top + rect.height / 2,
    amount: Math.min(12, bouquetFlower.quantity),
    emoji: emoji,
  })
  ;

  addToCart();
};


  const incrementQuantity = () => {
    if (bouquetFlower.quantity < flower.quantity) {
      setBouquetFlower({ flowerId: bouquetFlower.flowerId, quantity: ++bouquetFlower.quantity })
    }
  }
  const decrementQuantity = () => {
    if (bouquetFlower.quantity > 0) {
      setBouquetFlower({ flowerId: bouquetFlower.flowerId, quantity: --bouquetFlower.quantity })
    }
  }


  return (

  <div className={styles.perspectiveWrapper}>
    <div className={`${styles.flipContainer} ${front ? '' : styles.isFlipped}`}>

      <div className={`${styles.face} ${styles.front}`}>
        <FlowerCardFront
          selected={prop.selected && front}
          flower={flower}
          switch={() => setFront(!front)}
          burstRef={fxRef}
          bouquetDiff ={orderDiff}
        />
      </div>

      <div ref={ref} className={`${styles.face} ${styles.back}`}>
        <FlowerCardBack
          switch={() => setFront(!front)}
          flower={flower}
          bouquetFlower={bouquetFlower}
          addToCart={handleAddToCart}
          decrementQuantity={() => decrementQuantity()}
          incrementQuantity={() => incrementQuantity()}
          bouquetDiff={orderDiff}
        />
      </div>

    </div>
  </div>

  );
};