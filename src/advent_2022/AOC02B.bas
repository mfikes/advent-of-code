  5 T0=TIME
 10 T=0
 20 N=2500
 30 F$="AOC02I"
 40 ASSIGN# 1 TO F$
 50 FOR I=1 TO N
 60 READ# 1 ; X$
 70 L=NUM(X$)-64
 80 R=NUM(X$[3])-87
 90 IF R=2 THEN S=3 @ P=L @ GOTO 110
100 IF R=3 THEN S=6 @ P=RMD(L,3)+1 @ GOTO 110
105 S=0 @ P=RMD(L+1,3)+1
110 T=T+S+P
120 NEXT I
130 ASSIGN# 1 TO *
140 DISP T
145 DISP TIME-T0;"s"
150 END
