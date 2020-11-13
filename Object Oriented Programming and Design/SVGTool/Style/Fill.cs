using System;
using System.Collections.Generic;
using System.Text;

namespace SVGTool
{
    class Fill : IStyleElement
    {
        public Rgb Color { get; set; }
        /// <summary>
        /// Defines fill colour.
        /// </summary>
        /// <param name="r"></param>
        /// <param name="g"></param>
        /// <param name="b"></param>
        public Fill(int r, int g, int b)
        {
            Color = new Rgb(r, g, b);
        }
        public string ToSVGStyleString() => "fill:" + Color.ToSVGStyleString();
    }
}