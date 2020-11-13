using System;

namespace SVGTool
{
    public class Rgb
    {
        public int Red { get; set; }
        public int Green { get; set; }
        public int Blue { get; set; }

        /// <summary>
        /// Handle RGB inputs in the range of 0-255 for red, green and blue and provide valid SVG output.
        /// <para><example>The method returns the following SVG acceptable formatting: <code>rgb(255,255,255)</code></example></para>
        /// </summary>
        /// <exception cref="ArgumentOutOfRangeException">Input must be within 0-255 for each parameter.</exception>
        public Rgb(int red, int green, int blue)
        {
            if ((red > 255 | green > 255 | blue > 255) | (red < 0 | green < 0 | blue < 0))
                throw new ArgumentOutOfRangeException("One or more params is out of range 0-255.");

            Red = red;
            Green = green;
            Blue = blue;
        }

        // Default Constructor
        public Rgb()
        {
            Red = 255;
            Green = 255;
            Blue = 255;
        }

        public string ToSVGStyleString() => $"rgb({Red}, {Green}, {Blue})";
        public override string ToString() => $"R={Red} G={Green} B={Blue}";
    }
}