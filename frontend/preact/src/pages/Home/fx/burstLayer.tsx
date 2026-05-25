import { useEffect, useRef } from 'preact/hooks';
import { burstBus } from './burstBus';

export function BurstLayer() {
  const containerRef = useRef<HTMLDivElement | null>(null);
  const particles = useRef<any[]>([]);
  const raf = useRef<number>();

  const bounds = () => ({
  w: window.innerWidth,
  h: window.innerHeight,
});

  const createParticle = (x, y, emoji : string) => {
    const el = document.createElement('div');

    el.textContent = emoji;
    el.style.position = 'absolute';
    el.style.fontSize = '2.2rem';
    el.style.pointerEvents = 'none';

    containerRef.current?.appendChild(el);

    return {
      x,
      y,
      vx: (Math.random() - 0.5) * 40,
      vy: (Math.random() - 0.5) * 40,
      life: 90,
      el,
    };
  };

  useEffect(() => {
    const unsub = burstBus.subscribe(({ x, y, amount = 10, emoji }) => {
      for (let i = 0; i < amount; i++) {
        const num =  Math.floor(Math.random() * emoji.length)
        particles.current.push(
          createParticle(x, y, emoji[num])
        );
      }
    });

    return unsub;
  }, []);

  useEffect(() => {
    const loop = () => {
      const list = particles.current;

      const { w, h } = bounds();

const R = 20;

for (let i = list.length - 1; i >= 0; i--) {
  const p = list[i];

  // physics
  p.x += p.vx;
  p.y += p.vy;

  p.vx *= 0.98;
  p.vy *= 0.98;

  // gravity (important, otherwise it's just drift)
  p.vy += 0.6;

  // 🌸 BOUNDS (real fix)
  if (p.x < R) {
    p.x = R;
    p.vx *= -0.5;
  }

  if (p.x > w - R) {
    p.x = w - R;
    p.vx *= -0.5;
  }

  if (p.y < R) {
    p.y = R;
    p.vy *= -0.5;
  }

  if (p.y > h - R) {
    p.y = h - R;
    p.vy *= -0.3;
    p.vx *= 0.7;
  }

        p.vx *= 0.98;
        p.vy *= 0.98;

        p.life--;

        const out =
  p.x < -100 ||
  p.x > w + 100 ||
  p.y < -100 ||
  p.y > h + 100;

if (p.life <= 0 || out) {
  p.el?.remove();
  list.splice(i, 1);
  continue;
}

        p.el.style.transform =
          `translate(${p.x}px, ${p.y}px)`;
      }

      raf.current = requestAnimationFrame(loop);
    };

    loop();

    return () => cancelAnimationFrame(raf.current);
  }, []);

  return (
    <div
      ref={containerRef}
      style={{
        position: 'fixed',
        inset: 0,
        pointerEvents: 'none',
        zIndex: 9999,
      }}
    />
  );
}

export default BurstLayer