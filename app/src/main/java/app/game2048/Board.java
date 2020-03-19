package app.game2048;

/**
 *
 * @author revijanoca
 * June 2019
 *
 */
public class Board
{

    private int matrix[][];
    private int matrix_before_movement[][];
    private int max_rows;
    private int max_columns;
    private int score;
    /**
     *
     * @param max_rows
     * @param max_columns
     */
    public Board(int max_rows,int max_columns)
    {
        this.max_rows= max_rows;
        this.max_columns = max_columns;

        initializeMatrix();
        initializeScore();

    }

    /**
     *dd
     */
    private void initializeMatrix()
    {
        matrix = new int [max_rows][max_columns];
        matrix_before_movement = new int [max_rows][max_columns];
        for (int i=0;i<max_rows;i++)
        {
            for(int u=0;u<max_columns;u++)
            {
                matrix[i][u]=0;
            }
        }
        setNumber();
        setNumber();

    }
    /**
     *
     * @return result
     */
    private boolean isMatrixBeforeEqualsToCurrent()
    {
        boolean result = true;

        for (int i=0;i<max_rows;i++)
        {
            for(int u=0;u<max_columns;u++)
            {
                if(matrix[i][u] != matrix_before_movement[i][u])
                    result = false;

            }
        }

        return result;
    }

    /**
     *
     * @return
     */
    public boolean isGameOver()
    {
        // si el número de ceros es ninguno
        // y no ha habido movimiento anterior
        //el juego se ha acabado
        int counter = 0;
        //mirar el número de ceros
        for(int i=0;i<max_rows;i++)
            for(int u=0;u<max_columns;u++)
            {
                if(matrix[i][u] ==0)
                    counter++;
            }

        // si no hay ceros significa que no
        //quedan huecos por lo que la partida
        //ha finalizado

        if(counter==0 & !isPosibleMoveToLeft()
                & !isPosibleMoveToRight()
                & !isPosibleMoveToUp()
                & !isPosibleMoveToDown())
            return true;
        else
            return false;
    }

    /**
     *
     */
    public void setNumber()
    {
        int counter = 0;
        //mirar el número de ceros
        for(int i=0;i<max_rows;i++)
            for(int u=0;u<max_columns;u++)
            {
                if(matrix[i][u] ==0)
                    counter++;
            }

        if( counter>0)//si hay espacios
        {
            //se guarda la posición de los ceros
            int aux_vector[] = new int[counter];

            counter = 0;
            int position = 0;

            for(int i=0;i<max_rows;i++)
                for(int u=0;u<max_columns;u++)
                {
                    if(matrix[i][u] ==0)
                    {
                        aux_vector[counter] = position;
                        counter++;
                    }

                    position++;
                }

            //se busca una posición aleatoria en la que guardar
            //el número aletorio. Se genera un número entero
            //aleatorio en el intervalo cerrado: [0;counter-1]
            int random_position = (int)Math.floor(Math.random()*counter+1)-1;
            random_position = aux_vector[random_position];
            //guardar en la posición random un número aleatorio
            // 2 ó 4

            counter = 0;
            for(int i=0;i<max_rows;i++)
                for(int u=0;u<max_columns;u++)
                {
                    if(counter ==random_position)
                    {
                        matrix[i][u] = this.setRandomNumber_2_or_4();
                    }


                    counter++;
                }

        }

    }

    /**
     *
     * @return
     */
    public int getScore()
    {
        return score;
    }

    /**
     *
     */
    public void initializeScore()
    {
        score = 0;
    }

    /**
     *
     * @return
     */
    private int setRandomNumber_2_or_4()
    {
        //se genera un número decimal en el intervalo
        // abierto: (1,3). Con la función floor
        // las opciones se reducen a 1 o a 2
        int number_1_or_2 = (int) Math.floor(Math.random()*2+1);
        switch(number_1_or_2)
        {
            case 1://generar un 2

                break;

            case 2://generar un 4: tiene menos probabilidad que el 2

                number_1_or_2 = (int) Math.floor(Math.random()*2+1);

                break;

        }

        if(number_1_or_2 == 1)
            return 2;
        else
            return 4;
    }

    /**
     *
     * @return
     */
    public int getMax_rows()
    {
        return max_rows;
    }

    /**
     *
     * @return
     */
    public int getMax_columns()
    {
        return max_columns;
    }

    /**
     *
     * @return
     */
    public int [][] getMatrix()
    {
        return matrix;
    }

    /**
     *
     * @return
     */
    public String matrixToString()
    {
        String mat="";

        for (int i=0;i<max_rows;i++)
        {
            for(int u=0;u<max_columns;u++)
            {
                mat = mat+Integer.toString(matrix[i][u])+"\t";
            }
            mat = mat+"\n";
        }

        return mat;
    }

    /**
     *
     * @return
     */
    public boolean moveToLeft()
    {
        //guardar en matriz auxiliar la matriz original
        //para compararla después y saber si se ha producido un
        // movimiento

        for(int i=0;i<max_rows;i++)
            for(int u=0;u<max_columns;u++)
            {
                matrix_before_movement[i][u] = matrix[i][u];
            }



        for (int i=0;i<max_rows;i++)
        {

            //contar cuántos elementos son no nulos de la fila
            int counter = 0;
            for(int c=0;c<max_columns;c++)
            {
                if( matrix[i][c] != 0)
                    counter++;
            }
            //matriz fila auxiliar con elementos no nulos
            int aux_matrix[] = new int[counter];
            counter = 0;

            //copiar elementos no nulos a la matriz auxiliar
            for(int c=0;c<max_columns;c++)
            {
                if( matrix[i][c]!= 0)
                {
                    aux_matrix[counter] = matrix[i][c];
                    counter++;
                }

            }

            for(int c=0;c<aux_matrix.length-1;c++)
            {
                /*
                 *Si el valor de un elemnto es igual que el siguiente
                 *se suma y se desplaza hacia la izquierda el resto
                 *de elementos de la fila
                 */
                if(aux_matrix[c] == aux_matrix[c+1])
                {
                    //se suma
                    score = score +aux_matrix[c]+aux_matrix[c+1];
                    aux_matrix[c] = aux_matrix[c]+aux_matrix[c+1];

                    //desplazamiento hacia la izquierda
                    for(int d=c+1;d<aux_matrix.length;d++)
                    {
                        //en el último elemento se añade un cero
                        //por eso se mira si estamos en el
                        //último elemento
                        if( d+1 == aux_matrix.length)
                        {
                            aux_matrix[d] = 0;
                        }
                        else
                        {
                            aux_matrix[d] = aux_matrix[d+1];
                        }

                    }

                }

            }

            //copiar los elementos de la matriz auxiliar a la matriz
            //original. En los elementos restantes se añaden ceros

            for(int h=0;h<max_columns;h++)
            {

                if(h+1 > aux_matrix.length)
                {
                    matrix[i][h] = 0;
                }
                else
                {
                    matrix[i][h] = aux_matrix[h];
                }
            }


        }

        return !isMatrixBeforeEqualsToCurrent();

    }

    /**
     *
     * @return
     */
    public boolean moveToRight()
    {
        //guardar en matriz auxiliar la matriz original
        //para compararla después y saber si se ha producido un
        // movimiento
        for(int i=0;i<max_rows;i++)
            for(int u=0;u<max_columns;u++)
            {
                matrix_before_movement[i][u] = matrix[i][u];
            }

        for (int i=0;i<max_rows;i++)
        {
            //contar cuántos elementos son no nulos de la fila
            int counter = 0;
            for(int c=0;c<max_columns;c++)
            {
                if( matrix[i][c] != 0)
                    counter++;
            }
            //matriz fila auxiliar con elementos no nulos
            int aux_matrix[] = new int[counter];
            counter = 0;
            //copiar elementos no nulos a la matriz auxiliar
            for(int c=0;c<max_columns;c++)
            {
                if( matrix[i][c]!= 0)
                {
                    aux_matrix[counter] = matrix[i][c];
                    counter++;
                }

            }

            for(int c=aux_matrix.length-1;c>=1;c--)
            {

                /*
                 *Si el valor de un elemnto es igual que el anterior
                 *se suma y se desplaza hacia la izquierda el resto
                 *de elementos de la fila
                 */
                if( aux_matrix[c] == aux_matrix[c-1])
                {
                    // se suma
                    score = score +aux_matrix[c]+aux_matrix[c-1];
                    aux_matrix[c] = aux_matrix[c]+aux_matrix[c-1];

                    //desplazamiento hacia la derecha
                    for(int d=c-1;d>=0;d--)
                    {
                        //en el último elemento se añade un cero
                        //por eso se mira si estamos en el
                        //último elemento
                        if( d == 0)
                        {
                            aux_matrix[d] = 0;
                        }
                        else
                        {
                            aux_matrix[d] = aux_matrix[d-1];
                        }

                    }
                }


            }
            int length_aux_matrix=aux_matrix.length-1;
            for(int h=max_columns-1;h>=0;h--)
            {
                if(length_aux_matrix>=0)//h-aux_matrix.length>=0
                {
                    matrix[i][h] = aux_matrix[length_aux_matrix];
                    length_aux_matrix--;
                }
                else
                {
                    matrix[i][h] = 0;
                }
            }

        }


        return !isMatrixBeforeEqualsToCurrent();

    }

    /**
     *
     * @return
     */
    public boolean moveToUp()
    {
        //guardar en matriz auxiliar la matriz original
        //para compararla después y saber si se ha producido un
        // movimiento
        for(int i=0;i<max_rows;i++)
            for(int u=0;u<max_columns;u++)
            {
                matrix_before_movement[i][u] = matrix[i][u];
            }

        for(int i=0;i<max_columns;i++)
        {
            //contar cuántos elementos son no nulos de la columna
            int counter = 0;
            for(int r=0;r<max_rows;r++)
            {
                if( matrix[r][i] != 0)
                    counter++;
            }
            //matriz columna auxiliar con elementos no nulos
            int aux_matrix[] = new int[counter];
            counter = 0;
            //copiar elementos no nulos a la matriz auxiliar
            for(int r=0;r<max_rows;r++)
            {
                if( matrix[r][i]!= 0)
                {
                    aux_matrix[counter] = matrix[r][i];
                    counter++;
                }

            }

            for(int r=0;r<aux_matrix.length-1;r++)
            {
                /*
                 *Si el valor de un elemnto es igual que el siguiente
                 *se suma y se desplaza hacia la izquierda el resto
                 *de elementos de la fila
                 */
                if(aux_matrix[r] == aux_matrix[r+1])
                {
                    //se suma
                    score = score +aux_matrix[r]+aux_matrix[r+1];
                    aux_matrix[r] = aux_matrix[r]+aux_matrix[r+1];

                    //desplazamiento hacia la izquierda (hacia arriba)
                    for(int d=r+1;d<aux_matrix.length;d++)
                    {
                        //en el último elemento se añade un cero
                        //por eso se mira si estamos en el
                        //último elemento
                        if( d+1 == aux_matrix.length)
                        {
                            aux_matrix[d] = 0;
                        }
                        else
                        {
                            aux_matrix[d] = aux_matrix[d+1];
                        }

                    }

                }

            }
            //copiar los elementos de la matriz auxiliar a la matriz
            //original. En los elementos restantes se añaden ceros
            for(int h=0;h<max_rows;h++)
            {

                if(h+1 > aux_matrix.length)
                {
                    matrix[h][i] = 0;
                }
                else
                {
                    matrix[h][i] = aux_matrix[h];
                }
            }


        }


        return !isMatrixBeforeEqualsToCurrent();

    }

    /**
     *
     * @return
     */
    public boolean moveToDown()
    {
        //guardar en matriz auxiliar la matriz original
        //para compararla después y saber si se ha producido un
        // movimiento
        for(int i=0;i<max_rows;i++)
            for(int u=0;u<max_columns;u++)
            {
                matrix_before_movement[i][u] = matrix[i][u];
            }

        for (int i=0;i<max_columns;i++)
        {
            //contar cuántos elementos son no nulos de la fila
            int counter = 0;
            for(int r=0;r<max_rows;r++)
            {
                if( matrix[r][i] != 0)
                    counter++;
            }
            //matriz fila auxiliar con elementos no nulos
            int aux_matrix[] = new int[counter];
            counter = 0;
            //copiar elementos no nulos a la matriz auxiliar
            for(int r=0;r<max_rows;r++)
            {
                if( matrix[r][i]!= 0)
                {
                    aux_matrix[counter] = matrix[r][i];
                    counter++;
                }

            }

            for(int r=aux_matrix.length-1;r>=1;r--)
            {

                /*
                 *Si el valor de un elemnto es igual que el anterior
                 *se suma y se desplaza hacia la izquierda el resto
                 *de elementos de la fila
                 */
                if( aux_matrix[r] == aux_matrix[r-1])
                {
                    // se suma
                    score = score +aux_matrix[r]+aux_matrix[r-1];
                    aux_matrix[r] = aux_matrix[r]+aux_matrix[r-1];

                    //desplazamiento hacia la derecha
                    for(int d=r-1;d>=0;d--)
                    {
                        //en el último elemento se añade un cero
                        //por eso se mira si estamos en el
                        //último elemento
                        if( d == 0)
                        {
                            aux_matrix[d] = 0;
                        }
                        else
                        {
                            aux_matrix[d] = aux_matrix[d-1];
                        }

                    }
                }

            }

            int length_aux_matrix=aux_matrix.length-1;
            for(int h=max_rows-1;h>=0;h--)
            {
                if(length_aux_matrix>=0)//h-aux_matrix.length>=0
                {
                    matrix[h][i] = aux_matrix[length_aux_matrix];
                    length_aux_matrix--;
                }
                else
                {
                    matrix[h][i] = 0;
                }
            }

        }

        return !isMatrixBeforeEqualsToCurrent();

    }

    /**
     *
     * @return
     */
    public boolean isPosibleMoveToLeft()
    {

        boolean isPosible = false;

        for (int i=0;i<max_rows;i++)
        {


            //contar cuántos elementos son no nulos de la fila
            int counter = 0;
            for(int c=0;c<max_columns;c++)
            {
                if( matrix[i][c] != 0)
                    counter++;
            }
            //matriz fila auxiliar con elementos no nulos
            int aux_matrix[] = new int[counter];
            counter = 0;

            //copiar elementos no nulos a la matriz auxiliar
            for(int c=0;c<max_columns;c++)
            {
                if( matrix[i][c]!= 0)
                {
                    aux_matrix[counter] = matrix[i][c];
                    counter++;
                }

            }


            for(int c=0;c<aux_matrix.length-1;c++)
            {
                /*
                 *Si el valor de un elemnto es igual que el siguiente
                 *se suma y se desplaza hacia la izquierda el resto
                 *de elementos de la fila
                 */
                if(aux_matrix[c] == aux_matrix[c+1])
                {
                    //se puede sumar
                    isPosible = true;

                }

            }

        }

        return isPosible;


    }

    /**
     *
     * @return
     */
    public boolean isPosibleMoveToRight()
    {

        boolean isPosible = false;

        for (int i=0;i<max_rows;i++)
        {
            //contar cuántos elementos son no nulos de la fila
            int counter = 0;
            for(int c=0;c<max_columns;c++)
            {
                if( matrix[i][c] != 0)
                    counter++;
            }
            //matriz fila auxiliar con elementos no nulos
            int aux_matrix[] = new int[counter];
            counter = 0;
            //copiar elementos no nulos a la matriz auxiliar
            for(int c=0;c<max_columns;c++)
            {
                if( matrix[i][c]!= 0)
                {
                    aux_matrix[counter] = matrix[i][c];
                    counter++;
                }

            }

            for(int c=aux_matrix.length-1;c>=1;c--)
            {

                /*
                 *Si el valor de un elemnto es igual que el anterior
                 *se suma y se desplaza hacia la izquierda el resto
                 *de elementos de la fila
                 */
                if( aux_matrix[c] == aux_matrix[c-1])
                {
                    // se puede sumar
                    isPosible = true;

                }

            }

        }



        return isPosible;

    }

    /**
     *
     * @return
     */
    public boolean isPosibleMoveToUp()
    {

        boolean isPosible = false;

        for(int i=0;i<max_columns;i++)
        {
            //contar cuántos elementos son no nulos de la columna
            int counter = 0;
            for(int r=0;r<max_rows;r++)
            {
                if( matrix[r][i] != 0)
                    counter++;
            }
            //matriz columna auxiliar con elementos no nulos
            int aux_matrix[] = new int[counter];
            counter = 0;
            //copiar elementos no nulos a la matriz auxiliar
            for(int r=0;r<max_rows;r++)
            {
                if( matrix[r][i]!= 0)
                {
                    aux_matrix[counter] = matrix[r][i];
                    counter++;
                }

            }

            for(int r=0;r<aux_matrix.length-1;r++)
            {
                /*
                 *Si el valor de un elemnto es igual que el siguiente
                 *se suma y se desplaza hacia la izquierda el resto
                 *de elementos de la fila
                 */
                if(aux_matrix[r] == aux_matrix[r+1])
                {
                    //se puede sumar
                    isPosible = true;

                }

            }



        }

        return isPosible;

    }

    /**
     *
     * @return
     */
    public boolean isPosibleMoveToDown()
    {
        boolean isPosible = false;

        for (int i=0;i<max_columns;i++)
        {
            //contar cuántos elementos son no nulos de la fila
            int counter = 0;
            for(int r=0;r<max_rows;r++)
            {
                if( matrix[r][i] != 0)
                    counter++;
            }
            //matriz fila auxiliar con elementos no nulos
            int aux_matrix[] = new int[counter];
            counter = 0;
            //copiar elementos no nulos a la matriz auxiliar
            for(int r=0;r<max_rows;r++)
            {
                if( matrix[r][i]!= 0)
                {
                    aux_matrix[counter] = matrix[r][i];
                    counter++;
                }

            }

            for(int r=aux_matrix.length-1;r>=1;r--)
            {

                /*
                 *Si el valor de un elemnto es igual que el anterior
                 *se suma y se desplaza hacia la izquierda el resto
                 *de elementos de la fila
                 */
                if( aux_matrix[r] == aux_matrix[r-1])
                {
                    // se puede sumar
                    isPosible = true;

                }

            }


        }

        return isPosible;

    }


}
