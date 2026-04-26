
import Carousel from '../carousel/carousel';
import { Flower } from "../../api/api"
import FlowerCard from './flowerCardComp';
import { useDataContext } from '../../pages/Home/Provider/dataProvider';

const FlowerCarousel = () => {
  const { flowers } = useDataContext()


  return (
    <>
      {flowers != null && flowers.length != 0 ?
        (<Carousel
          data={flowers}
          renderItem={(flower: Flower, index: Number, isActive: boolean) => (
            <FlowerCard selected={isActive} key={flower.id} flower={flower}>

            </FlowerCard>
          )}>

        </Carousel>)
        : (<div>Loading</div>)}

    </>


  );
};

export default FlowerCarousel;