Name: Xiwei Wang

Foreword:
Many years ago, I thought every computer game is made up by "if" statement completely(all the possible action and game environment's combination). If the player take this kind of action, then if the current status is that kind, the output can be find directly. Here I use tons of if statement since each direction the way of comparing is different. I know this is not a good way of programming, but it needs extremely clear logic, which can be a good practice.

Rule:
Run Project1.java directly. The player will be asked to input w,a,s,d for moving up,left,right,down; q for quit, r for restart. Game rule is exactly the same as the well-known 2048 game.



I didn't write in the comment about my moving logic, so I write it here: my "move" method is separated into three methods: combine, full, arrange. All of them takes the direction as parameter, and check from the side we want to move to to the opposite.(ex: input a, the methods will check from the left side of the board.)

Combine(void): check on the direction if there exists two adjacent(or only 0 between them in that direction) identical numbers. If so, double the number in that direction and change the other to 0; If not, set a public boolean invalid to true.

Full(boolean): if invalid is true, then check if there's space to move towards the direction. If not, return false. In the main function, if both invalid and full, then this is an "invalid"(the real invalid meaning) move, so continue the loop from the start to ask for another input.

Arrange(void): if either valid or not full, just move all the numbers to that direction.