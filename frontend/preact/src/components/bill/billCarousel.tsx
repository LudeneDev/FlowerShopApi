import { Bill } from "@api/api";
import { useDataContext } from "../../pages/Home/Provider/dataProvider";
import Carousel from "../carousel/carousel";
import BillCard from "./billCard";



export default function billCarousel() {

    const { bills } = useDataContext();



    return (
        <>
            {bills != null && bills.length != 0 ? (
                <Carousel
                    data={bills}
                    renderItem={(item: Bill, index: Number, isActive: boolean) => (
                        <BillCard key={item.id} bill={item}>

                        </BillCard>
                    )

                    }

                >


                </Carousel>) : (<div>Loading</div>)}
        </>
    )
}