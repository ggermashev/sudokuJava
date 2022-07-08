package sample;

import java.util.LinkedList;

public class Sudoku {
    final int choice[] = {1,2,3,4,5,6,7,8,9};
    LinkedList<Integer> allowedHorizont[];
    LinkedList<Integer> allowedVertical[];
    LinkedList<Integer> allowedSquare[];
    Integer field[][];
    int startCount;

    Sudoku(int _startCount) {
        field = new Integer[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field[i][j] = 0;
            }
        }
        startCount = _startCount;
        allowedHorizont = new LinkedList[9];
        allowedVertical = new LinkedList[9];
        allowedSquare = new LinkedList[9];
        for (int k = 0; k < 9; k++) {
            allowedSquare[k] = new LinkedList<Integer>();
            allowedHorizont[k] = new LinkedList<Integer>();
            allowedVertical[k] = new LinkedList<Integer>();
            for (int i = 1; i < 10; i++) {
                allowedSquare[k].add(i);
                allowedHorizont[k].add(i);
                allowedVertical[k].add(i);
            }
        }
    }

    int getRandomCell()
    {
        return (int)(Math.random() * 81);
    }

    void updateAllows()
    {
        for (int i = 0; i < 9; i++)
        {
            int y = 3*(i/3);
            int x = 3*(i%3);
            for (int k = 0; k < 9; k++)
            {
                if (field[i][k] != 0) allowedHorizont[i].removeFirstOccurrence(field[i][k]);
                if (field[k][i] != 0) allowedVertical[i].removeFirstOccurrence(field[k][i]);
                if (field[y + k / 3][x + k % 3] != 0) allowedSquare[i].removeFirstOccurrence(field[y + k / 3][x + k % 3]);
            }
        }
    }

    void updateAllows(Integer[][] field, LinkedList<Integer>[] hor, LinkedList<Integer>[] vert, LinkedList<Integer>[] sq)
    {
        for (int k = 0; k < 9; k++)
        {
            hor[k].clear();
            vert[k].clear();
            sq[k].clear();
            for (int i = 1; i < 10; i++)
            {
                sq[k].add(i);
                hor[k].add(i);
                vert[k].add(i);
            }
        }

        for (int i = 0; i < 9; i++)
        {
            int y = 3*(i/3);
            int x = 3*(i%3);
            for (int k = 0; k < 9; k++)
            {
                if (field[i][k] != 0) hor[i].removeFirstOccurrence(field[i][k]);
                if (field[k][i] != 0) vert[i].removeFirstOccurrence(field[k][i]);
                if (field[y + k / 3][x + k % 3] != 0) sq[i].removeFirstOccurrence(field[y + k / 3][x + k % 3]);
            }
        }
    }

    void printAllowed(LinkedList<Integer>[] allowedHorizont, LinkedList<Integer>[] allowedVertical, LinkedList<Integer>[] allowedSquare)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int k = 0; k < allowedVertical[i].size(); k++) System.out.print(allowedVertical[i].get(k) + " ");
        }
        System.out.println();
        for (int i = 0; i < 9; i++)
        {
            for (int k = 0; k < allowedHorizont[i].size(); k++) System.out.print(allowedHorizont[i].get(k) + " ");
        }
        System.out.println();
        for (int i = 0; i < 9; i++)
        {
            for (int k = 0; k < allowedSquare[i].size(); k++) System.out.print(allowedSquare[i].get(k) + " ");
        }
        System.out.println("\n-----------------------\n");
    }


    int fillCount(int y, int x)
    {
        LinkedList<Integer> choice = new LinkedList<Integer>();
        for (int i = 1; i < 10; i++)
        {
            if (allowedHorizont[y].contains(i) && allowedVertical[x].contains(i) && allowedSquare[3*(y/3) + x/3 ].contains(i))
            {
                choice.add(i);
            }
        }
        int ind = (int)(Math.random() * choice.size());
        //System.out.println(ind);
        return choice.get(ind);
    }

    void printField()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    void printField(int[][] field)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    void createField()
    {
        for (int i = 0; i < startCount; i++)
        {
            int num = getRandomCell();
            int x = (num) % 9;
            int y = (num) / 9;
            field[y][x] = fillCount(y,x);
            updateAllows();
        }
        //printAllowed();
        printField();
    }

    Integer[][] copy(Integer[][] arr)
    {
        Integer[][] res = new Integer[9][9];
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                res[i][j] = arr[i][j];
            }
        }
        return res;
    }

    LinkedList<Integer>[] copy(LinkedList<Integer>[] list)
    {
        LinkedList<Integer> res[] = new LinkedList[9];
        for (int k = 0; k < 9; k++)
        {
            res[k] = new LinkedList<Integer>();
            for (int i = 0; i < list[k].size(); i++)
            {
                res[k].add(list[k].get(i));
            }
        }
        return res;
    }

    boolean checkZero(Integer[][] field)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (field[i][j] == 0) return true;
            }
        }
        return false;
    }


    Integer[][] solv(Integer[][] field, LinkedList<Integer>[] hor, LinkedList<Integer>[] vert, LinkedList<Integer>[] sq)
    {
        updateAllows(field, hor, vert, sq);
        //printAllowed(hor, vert, sq);
        //printField(field);
        boolean flag = false;
        if (!checkZero(field))
        {
            this.field = field;
            return field;
        }
        for (int y = 0; y < 9; y++)
        {
            for (int x = 0; x < 9; x++) {
                if (field[y][x] == 0) {
                    flag = true;
                    for (int k = 1; k < 10; k++)
                    {
                        if (hor[y].contains(k) && vert[x].contains(k) && sq[3 * (y / 3) + x / 3].contains(k))
                        {
                            Integer cpField[][] = copy(field);
                            hor[y].removeFirstOccurrence(k);
                            vert[x].removeFirstOccurrence(k);
                            sq[3 * (y / 3) + x / 3].removeFirstOccurrence(k);
                            LinkedList<Integer> cphor[] = copy(hor);
                            LinkedList<Integer> cpvert[] = copy(vert);
                            LinkedList<Integer> cpsq[] = copy(sq);
                            cpField[y][x] = k;
                            Integer res[][] = solv(cpField, cphor, cpvert, cpsq);
                            if (res != null)
                            {
                                if (!checkZero(res)) return res;
                            }
                        }
                    }
                    return null;
                }
            }
        }
        if (!flag)
        {
            this.field = copy(field);
            //printField();
        }
        return null;
    }

}
