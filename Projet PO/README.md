:3 

S - spawn case enemy (1)       Color.RED
B - base case player (1)       Color.ORANGE
R - route cases (+)            Color(194,178,128)
C - constructable cases (+)    Color.LIGHT_GRAY
X - backround/border cases (+) Color(11,102,35)


grille manuelle:
StdDraw.setPenColor(new Color(44, 62, 80));
for(int x=0; x<=scale; x++){//grille
    StdDraw.line(x*(700/scale),0, x*(700/scale), 700);
}
for (int y=0; y<=scale; y++){
    StdDraw.line(0, y*(700/scale), 700, y*(700/scale));
}