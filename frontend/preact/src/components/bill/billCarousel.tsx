import { Bill } from "@api/api";
import { useDataContext } from "../../pages/Home/Provider/dataProvider";
import Carousel from "../carousel/carousel";
import BillCard, { EmptyBillCard } from "./billCard";



export default function billCarousel() {

    const { bills } = useDataContext();



    return (
        <>
            {bills != null ? (
                <Carousel
                    data={bills}
                    renderItem={(item: Bill | null, index: Number, isActive: boolean) => (


                        item != null ? (<BillCard key={item.id} bill={item} />) : (<EmptyBillCard />)



                    )

                    }

                >


                </Carousel>) : (<div>Loading</div>)}
        </>
    )
}