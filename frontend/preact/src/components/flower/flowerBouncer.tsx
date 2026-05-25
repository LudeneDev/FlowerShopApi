import { useEffect, useRef, useState } from 'preact/hooks';



export type FlowerBouncerApi = {
  init: (amount?: number, emojis: string[]) => void;

};

const PARTICLE_SIZE = Math.max(
    16,
    Math.min(window.innerHeight * 0.04, 60)
);
const R = PARTICLE_SIZE / 2;



export default function FlowerBouncer({ active ,onReady }) {


  // new ref to track the latest active value
const activeRef = useRef(active);
useEffect(() => {
  activeRef.current = active;
}, [active]);

  const containerRef = useRef<HTMLDivElement | null>(null);

  // 🌸 persistent bouquet
  const bouquetRef = useRef<any[]>([]);



  const rafRef = useRef<number>();

  const sizeRef = useRef({
    width: 0,
    height: 0,
  });

  // -------------------------
  // expose API
  // -------------------------
  useEffect(() => {
    onReady?.({
      init
    });
  }, []);

  // -------------------------
  // resize tracking
  // -------------------------
  useEffect(() => {
    if (!containerRef.current) return;

    const el = containerRef.current;

    const updateSize = () => {
      const rect = el.getBoundingClientRect();

      sizeRef.current = {
        width: rect.width,
        height: rect.height,
      };
    };

    updateSize();

    const ro = new ResizeObserver(updateSize);
    ro.observe(el);

    return () => ro.disconnect();
  }, []);

  // -------------------------
  // helpers
  // -------------------------
  function createElement(emoji : string) {
    const el = document.createElement('div');

    el.textContent = emoji;

    el.style.position = 'absolute';
    el.style.left = '0px';
    el.style.top = '0px';

    el.style.fontSize = PARTICLE_SIZE+"px";

    el.style.pointerEvents = 'none';
    el.style.userSelect = 'none';

    el.style.willChange = 'transform';

    containerRef.current?.appendChild(el);

    return el;
  }

  function hasValidSize() {
    return (
      sizeRef.current.width > 0 &&
      sizeRef.current.height > 0
    );
  }

  // -------------------------
  // INIT (persistent bouquet)
  // -------------------------
  function init(amount = 10, emojis : string[]) {
    if (!hasValidSize()) {
      requestAnimationFrame(() => init(amount, emojis));
      return;
    }

    // cleanup previous bouquet
    for (const p of bouquetRef.current) {
      p.el?.remove();
    }

    bouquetRef.current = [];

    const { width, height } = sizeRef.current;

    for (let i = 0; i < amount; i++) {
      const num = Math.floor(Math.random() * emojis.length)
      const emoji = emojis[num].toString()
      setTimeout(() => {
        const particle = {
          x: width / 2,
          y: height - R,

          vx: (Math.random() - 0.5) * 1.2,
          vy: -(1 + Math.random()),

          rotation: 0,
          dRotation: (Math.random() - 0.5) * 0.8,

          scale: 0.9 + Math.random() * 0.3,

          settled: false,
          settleTimer: 0,

          el: createElement(emoji),
        };

        bouquetRef.current.push(particle);

      }, i * 120);
    }
  }




  // -------------------------
  // animation loop
  // -------------------------
  useEffect(() => {
    const step = () => {

      if(!activeRef.current){
        rafRef.current = requestAnimationFrame(step);
        return
      }
      const { width, height } = sizeRef.current;

      // =====================================================
      // 🌸 BOUQUET PHYSICS
      // =====================================================

      const bouquet = bouquetRef.current;

      const minX = R;
      const maxX = width - R;
      const floorY = height - R;

      for (const p of bouquet) {
        if (!p.el) continue;

        // gravity
        p.vy += 0.35;

        // damping
        p.vx *= 0.98;
        p.vy *= 0.98;

        // movement
        p.x += p.vx;
        p.y += p.vy;

        // rotation
        if (!p.settled) {
          p.rotation += p.dRotation;
        }

        // walls
        if (p.x < minX) {
          p.x = minX;
          p.vx *= -0.4;
        }

        if (p.x > maxX) {
          p.x = maxX;
          p.vx *= -0.4;
        }

        // floor
        if (p.y >= floorY) {
          p.y = floorY;

          p.vy *= -0.2;
          p.vx *= 0.7;

          const motion =
            Math.abs(p.vx) +
            Math.abs(p.vy) +
            Math.abs(p.dRotation);

          if (motion < 0.08) {
            p.settleTimer++;
          } else {
            p.settleTimer = 0;
          }

          if (p.settleTimer > 50) {
            p.settled = true;

            p.vx = 0;
            p.vy = 0;
            p.dRotation = 0;
          }
        }
      }

      // -------------------------
      // bouquet collisions
      // -------------------------
      for (let i = 0; i < bouquet.length; i++) {
        const a = bouquet[i];

        for (let j = i + 1; j < bouquet.length; j++) {
          const b = bouquet[j];

          if (a.settled && b.settled) continue;

          const dx = b.x - a.x;
          const dy = b.y - a.y;

          const dist = Math.sqrt(dx * dx + dy * dy);

          const minDist = R * 2;

          if (dist === 0 || dist >= minDist) continue;

          const overlap = (minDist - dist) / 2;

          const nx = dx / dist;
          const ny = dy / dist;

          a.x -= nx * overlap;
          a.y -= ny * overlap;

          b.x += nx * overlap;
          b.y += ny * overlap;

          const tx = a.vx;
          const ty = a.vy;

          a.vx = b.vx * 0.85;
          a.vy = b.vy * 0.85;

          b.vx = tx * 0.85;
          b.vy = ty * 0.85;
        }
      }
      // =====================================================
      // render bouquet
      // =====================================================

      for (const p of bouquet) {
        if (!p.el) continue;

        p.el.style.transform =
          `translate(${p.x - R}px, ${p.y - R}px)
           rotate(${p.rotation}deg)
           scale(${p.scale})`;
      }





      rafRef.current = requestAnimationFrame(step);
    };

    rafRef.current = requestAnimationFrame(step);

    return () => {
      cancelAnimationFrame(rafRef.current);
    };
  }, [activeRef.current]);

  // -------------------------
  return (
    <div
      ref={containerRef}
      style={{
        position: 'relative',
        width: '100%',
        height: '100%',

        overflow: 'visible',

        pointerEvents: 'none',
      }}
    />
  );
}






