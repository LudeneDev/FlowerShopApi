const FLOWER_EMOJIS: Record<string, string> = {
    rose: '🌹',
    tulip: '🌷',
    sunflower: '🌻',
    cherry: '🌸',
    blossom: '🌸',
    lavender: '🪻',
    hibiscus: '🌺',
};

const DEFAULT_FLOWERS = [
    '🌷'.trim().toLowerCase(),
    '🌸'.trim().toLowerCase(),
    '🌹'.trim().toLowerCase(),
    '🌻'.trim().toLowerCase(),
    '🌺'.trim().toLowerCase(),
    '🪻'.trim().toLowerCase(),
];



export default function getFlowerEmoji(
    flowerName?: string
):  string[] {

    if (!flowerName || FLOWER_EMOJIS[flowerName.trim().toLowerCase()] === undefined) {
        return DEFAULT_FLOWERS;
    }

    const normalized =
        flowerName.trim().toLowerCase();

    return (
        [FLOWER_EMOJIS[normalized]]
    );
}