#pragma checksum "/Users/etiennecosta/Desktop/LI4/MentorMe/Views/Home/RefinedSearch.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "824f3b190f403aee2111ee3209f984e77f1a5c86"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_Home_RefinedSearch), @"mvc.1.0.view", @"/Views/Home/RefinedSearch.cshtml")]
namespace AspNetCore
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.AspNetCore.Mvc.Rendering;
    using Microsoft.AspNetCore.Mvc.ViewFeatures;
#nullable restore
#line 1 "/Users/etiennecosta/Desktop/LI4/MentorMe/Views/_ViewImports.cshtml"
using MentorMe;

#line default
#line hidden
#nullable disable
#nullable restore
#line 2 "/Users/etiennecosta/Desktop/LI4/MentorMe/Views/_ViewImports.cshtml"
using MentorMe.Models;

#line default
#line hidden
#nullable disable
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"824f3b190f403aee2111ee3209f984e77f1a5c86", @"/Views/Home/RefinedSearch.cshtml")]
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"cbab53756600d2da816218cdb97ce93e400b173e", @"/Views/_ViewImports.cshtml")]
    public class Views_Home_RefinedSearch : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<MentorMe.Models.Home>
    {
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
            WriteLiteral("\r\n\r\n\r\n<div class=\"lefty\" style=\"width:30%\">\r\n    <p style=\"color:blue\">Search results:</p>\r\n\r\n\r\n\r\n");
#nullable restore
#line 10 "/Users/etiennecosta/Desktop/LI4/MentorMe/Views/Home/RefinedSearch.cshtml"
     if (ViewBag.RoomCount == 0)
    {

#line default
#line hidden
#nullable disable
            WriteLiteral("        <p style=\"font-size: small; color:grey;\">-No Rooms here, try searching again :) </p>\r\n");
#nullable restore
#line 13 "/Users/etiennecosta/Desktop/LI4/MentorMe/Views/Home/RefinedSearch.cshtml"
    }
    else
    {

#line default
#line hidden
#nullable disable
            WriteLiteral("        <div class=\"list-group\">\r\n");
#nullable restore
#line 17 "/Users/etiennecosta/Desktop/LI4/MentorMe/Views/Home/RefinedSearch.cshtml"
             foreach (var room in ViewBag.Rooms)
            {

#line default
#line hidden
#nullable disable
            WriteLiteral("                <a style=\"margin-bottom:5px;\"");
            BeginWriteAttribute("href", " href=\'", 430, "\'", 508, 1);
#nullable restore
#line 19 "/Users/etiennecosta/Desktop/LI4/MentorMe/Views/Home/RefinedSearch.cshtml"
WriteAttributeValue("", 437, Url.Action("RefineSearchAction", "Room", new { RoomID = room.RoomID }), 437, 71, false);

#line default
#line hidden
#nullable disable
            EndWriteAttribute();
            WriteLiteral(" class=\"list-group-item list-group-item-action list-group-item-primary\">");
#nullable restore
#line 19 "/Users/etiennecosta/Desktop/LI4/MentorMe/Views/Home/RefinedSearch.cshtml"
                                                                                                                                                                                               Write(room.Nome);

#line default
#line hidden
#nullable disable
            WriteLiteral("</a>\r\n");
#nullable restore
#line 20 "/Users/etiennecosta/Desktop/LI4/MentorMe/Views/Home/RefinedSearch.cshtml"
            }

#line default
#line hidden
#nullable disable
            WriteLiteral("        </div>\r\n");
#nullable restore
#line 22 "/Users/etiennecosta/Desktop/LI4/MentorMe/Views/Home/RefinedSearch.cshtml"
    }

#line default
#line hidden
#nullable disable
            WriteLiteral("\r\n</div>");
        }
        #pragma warning restore 1998
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.ViewFeatures.IModelExpressionProvider ModelExpressionProvider { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IUrlHelper Url { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IViewComponentHelper Component { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IJsonHelper Json { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IHtmlHelper<MentorMe.Models.Home> Html { get; private set; }
    }
}
#pragma warning restore 1591
