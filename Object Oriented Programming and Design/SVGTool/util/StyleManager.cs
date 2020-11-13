using System.Collections.Generic;
using System.Text;

namespace SVGTool
{
    public class StyleManager
    {
        /*
         * StyleManager provides unified management of style elements for any shape that implements this class.
         */
        protected HashSet<IStyleElement> StyleElements { get; set; }
        /// <summary>
        /// Add a new style element to this object.
        /// </summary>
        /// <param name="styleElement">Pre-configured Style Element to be added.</param>
        public void AddStyleElement(IStyleElement styleElement)
        {
            StyleElements.Add(styleElement);
        }

        /// <summary>
        /// Appends the compiled list of StyleElement objects into a StringBuilder that describes the styling in SVG format.
        /// </summary>
        /// <param name="exportString">StringBuilder to append styling to.</param>
        /// <param name="styleElements">Set of Style Elements to apply.</param>
        public void ApplyStyleElements(StringBuilder exportString, HashSet<IStyleElement> styleElements)
        {
            if (styleElements.Count > 0)
            {
                exportString.Append(" style=\"");
                foreach (IStyleElement style in styleElements)
                {
                    exportString.Append(style.ToSVGStyleString()).Append(";");
                }
                FinalizeStyleList(exportString);
            }
        }

        private static void FinalizeStyleList(StringBuilder exportString)
        {
            exportString.Replace(";", "\"", exportString.Length - 1, 1);
        }
    }
}
