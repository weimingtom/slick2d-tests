uniform sampler2D rscene;
varying vec2 texcoord;
 
void main()
{
    vec4 pxl = texture2D(rscene, texcoord);
    float avg = ((pxl.r + pxl.g + pxl.b) / 3.0);
 
    // bloom
    {
        vec4 sum = vec4(0.0);
        for (int i = -5; i <= 5; i++)
        {
            for (int j = -5; j <= 5; j++)
            {
             sum += texture2D(rscene, (texcoord + vec2(i, j)
                   * 0.0018)) * 0.015;
            }
        }
 
        if (avg < 0.025)
        {
            gl_FragColor = pxl + sum * 0.335;
        }
        else if (avg < 0.10)
        {
            gl_FragColor = pxl + (sum * sum) * 0.5;
        }
        else if (avg < 0.88)
        {
            gl_FragColor = pxl + ((sum * sum) * 0.333);
        }
        else if (avg >= 0.88)
        {
            gl_FragColor = pxl + sum;
        }
        else
        {
            gl_FragColor = pxl;
        }
    }
}