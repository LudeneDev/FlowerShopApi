import Carousel from '../carousel/carousel';
import {Flower} from "@api/api"
import FlowerCard from './flowerCardComp';
import {useDataContext} from '../../pages/Home/Provider/dataProvider';
import CreateFlowerCard from "./createFlowerCard";
import CarouselItem from "../carousel/carouselItem";

interface FlowerCarouselProps {
    adminMode?: boolean
}

type FlowerEntry =
  | Flower
  | {type : "create"}


const FlowerCarousel = (prop : FlowerCarouselProps) => {
    const {flowers} = useDataContext()
    const data : FlowerEntry[] = [
      ...(flowers ?? []),
      ...(prop.adminMode ? [{ type: "create" as const }] : []),

    ];


    return (
        <>
            {flowers != null && flowers.length != 0 ?
                (<Carousel
                    data={data}
                    renderItem={(flower: FlowerEntry, index: Number, isActive: boolean) => {

                        if(prop.adminMode && flower.type === "create"){
                        return <CreateFlowerCard selected={isActive} key={"create"} />
                    }

                        return <FlowerCard selected={isActive} key={flower.id} flower={flower}/>


            }}>


                </Carousel>)
                : (<div>Loading</div>)}

        </>


    );
};

export default FlowerCarousel;