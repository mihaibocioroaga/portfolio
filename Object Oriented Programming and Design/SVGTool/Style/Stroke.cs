using System;
using System.Collections.Generic;
using System.Text;

namespace SVGTool
{
    class Stroke : IStyleElement
    {
        public Rgb Color { get; set; }
        /// <summary>
        /// Defines stroke colour.
        /// </summary>
        /// <param name="r"></param>
        /// <param name="g"></param>
        /// <param name="b"></param>
        public Stroke(int r, int g, int b)
        {
            Color = new Rgb(r, g, b);
        }
        public string ToSVGStyleString() => "stroke:" + Color.ToSVGStyleString();
    }
}