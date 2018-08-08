pico-8 cartridge // http://www.pico-8.com
version 16
__lua__
--by beetrootmonkey

--@import lib
function dosomething()
  mvp()
  for i=1,#enemies do
    if ecoll(p,enemies[i]) then
      enemies[i].dead=true
    end
    turne(enemies[i])
    mve(enemies[i])
  end
  collect()
end


p={}
enemies={}
dbm=0
espd=1
pspd=1



function _init()
  camera(4,4)
  initplayer()
  initenemies()
end

function _update()
	if btn(➡�?) then setdir(p,0) end
  if btn(⬇�?) then setdir(p,1) end
  if btn(⬅�?) then setdir(p,2) end
  if btn(⬆�?) then setdir(p,3) end

  dosmtg()
end

function _draw()
  cls()
  map(0,0,0,0,20,20)
  for i=1,#enemies do
    drawe(enemies[i])
  end
  drawp()
  print(fill(p.sc,7),18,17,7)

end


__gfx__
0000000000000000000000000000000000aaaa0000aaaa0000aaaa00000000001000000011111111000000000000000000000000000000000000000000000000
000000000000000000000000000000000aaaaaa00aaaaaa00aaaaaa00a7007a01000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000bbbb00aa007070aa0ee0aa070700aaaa0000aa1000000000000000000000000000000000000000000000000000000000000000
00000000000000000009900000bbbb00aa000000aa0ee0aa000000aaaa7007aa1000000000000000000000000000000000000000000000000000000000000000
00000000000000000009900000bbbb00aa000000aa7007aa000000aaaa0000aa1000000000000000000000000000000000000000000000000000000000000000
00000000007007000000000000bbbb00aaee7070aa0000aa0707eeaaaa0ee0aa1000000000000000000000000000000000000000000000000000000000000000
000000000000000000000000000000000aaaaaa00a7007a00aaaaaa00aaaaaa01000000000000000000000000000000000000000000000000000000000000000
0000000000000000000000000000000000aaaa000000000000aaaa0000aaaa001000000000000000000000000000000000000000000000000000000000000000
011111100111111111111111111111100000000000aaaa0000000000000000000000000000000001000000000000000000000000000000000000000000000000
100000011000000000000000000000010aaaaa000aaaaaa000aaaaa000a77a000000000000000001000000000000000000000000000000000000000000000000
10000001100000000000000000000001aaaaaaa00aaeeaa00aaaaaaa0aa00aa00000000000000001000000000000000000000000000000000000000000000000
10000001100000000000000000000001aa0070700aaeeaa0070700aa0aa77aa00000000000000001000000000000000000000000000000000000000000000000
10000001100000000000000000000001aaee70700aa77aa00707eeaa0aa00aa00000000000000001000000000000000000000000000000000000000000000000
10000001100000000000000000000001aaaaaaa00aa00aa00aaaaaaa0aaeeaa00000000000000001000000000000000000000000000000000000000000000000
100000011000000000000000000000010aaaaa0000a77a0000aaaaa00aaaaaa00000000000000001000000000000000000000000000000000000000000000000
1000000101111111111111111111111000000000000000000000000000aaaa001111111100000001000000000000000000000000000000000000000000000000
10000001011111111111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
1000000110000000000000010004330000cccc0000cccc0000cccc0000cccc00005cccc000000000000000000000000000000000000000000000000000000000
100000011000000000000001000400000cccccc00cccccc00cccccc00cccccc0005cccc000000000000000000000000000000000000000000000000000000000
100000011000000000000001008888000cccccc00cccccc00cccccc00cccccc0005cccc000000000000000000000000000000000000000000000000000000000
100000011000000000000001008888000cc7cc700cccccc007cc7cc00cccccc00050000000000000000000000000000000000000000000000000000000000000
100000011000000000000001008888000cccccc00c7cc7c00cccccc00cccccc00050000000000000000000000000000000000000000000000000000000000000
100000011000000000000001008888000cccccc00cccccc00cccccc00cccccc00050000000000000000000000000000000000000000000000000000000000000
100000011000000000000001000000000c0cc0c00c0cc0c00c0cc0c00c0cc0c00000000000000000000000000000000000000000000000000000000000000000
10000001100000000000000100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
100000011000000000000001005aaaa000cccc0000cccc0000cccc0000cccc000000000000000000000000000000000000000000000000000000000000000000
100000011000000000000001005aaaa00cccccc00cccccc00cccccc00cccccc00000000000000000000000000000000000000000000000000000000000000000
100000011000000000000001005aaaa00cccccc00cccccc00cccccc00cccccc00000000000000000000000000000000000000000000000000000000000000000
100000011000000000000001005000000cc7cc700cccccc007cc7cc00cccccc00000000000000000000000000000000000000000000000000000000000000000
100000011000000000000001005000000cccccc00c7cc7c00cccccc00cccccc00000000000000000000000000000000000000000000000000000000000000000
100000011000000000000001005000000cccccc00cccccc00cccccc00cccccc00000000000000000000000000000000000000000000000000000000000000000
0111111001111111111111100000000000c00c0000c00c0000c00c0000c00c000000000000000000000000000000000000000000000000000000000000000000
__gff__
0000020400000000010100000000000001010101000000000101000000000000010101080000000000000000000000000101010000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
__map__
2112121212121212121302211212121222000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2003020202020202020202200202020320000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2002211212130211220211181302100220000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2002200202020202200202020202200220000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
3002311302112202311302111212320230000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
0202020202022002280202280202020202000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2113022113023112130221121302111222000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2002022002020202020220020202020220000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2002113202111212220230022112130220000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2028020202280202300202282002020220000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
0812121302112202020211121812121219000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2002020202023112130202020202020220000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2002211213020202020221121302100220000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2002200202021002211219020202200220000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2002300211123202303330021112320220000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
2003020202020202020202020202020320000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
3112121212121212121302111212121232000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
__sfx__
000400000153014530145000d5001450021500305000000000000000002f1002f1002e1002e100000000000000000262002620025200242002420000000000000000000000000000000000000000000000000000
00060000015100d5301555004510105301a5500751015530215500170001700017000170001700017000170001700017000170001700017000170001700017000170001700017000170001700017000170001700
001100000ca501ca001ba001ba0014a501aa0019a0019a000ca5016a0016a0015a0014a500ba002aa0016a000ca5018a0009a001aa0014a500ca000ca000ca000ca500ca000ca000ca0014a500ca000ca000ca00
__music__
00 40424344
