import { useTheme } from "@mui/material";

export default function IndicatorBar({ data, activeIndex, setActiveIndex }) {
    const theme = useTheme();
    const isDark = theme.palette.mode === "dark";

    return (
        <div
            style={{
                display: "flex",
                alignItems: "flex-end",
                justifyContent: "center",
                gap: "6px",
                marginTop: "16px",
                height: "20px",
            }}
        >
            {data.map((_, index) => {
                const distance = Math.abs(index - activeIndex);

                const scale =
                    distance === 0 ? 1.8 :
                        distance === 1 ? 1.2 :
                            0.6;

                return (
                    <div
                        key={index}
                        onClick={() => setActiveIndex(index)}
                        style={{
                            width: "10px",
                            height: "6px",
                            borderRadius: "6px",
                            cursor: "pointer",

                            transform: `scaleY(${scale})`,
                            transition: "all 0.3s ease",


                            background: isDark
                                ? "rgba(255,255,255,0.8)"
                                : "rgba(0,0,0,0.6)",

                            opacity: distance > 2 ? 0.3 : 1,
                        }}
                    />
                );
            })}
        </div>
    );
}